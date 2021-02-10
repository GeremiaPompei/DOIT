package it.unicam.cs.ids.DOIT.entity.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProgramManagerRole extends Role implements IPendingRole<ProgramManagerRole>, IPartecipationRequestHandler<DesignerRole> {

    public final static String TYPE = "program-manager";

    @Id
    @Column(name = "id_program_manager")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"pendingRole", "team"})
    private Set<PartecipationRequest<ProgramManagerRole>> myPartecipationRequests;

    public ProgramManagerRole() {
    }

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
        this.myPartecipationRequests = new HashSet<>();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void acceptPR(PartecipationRequest<DesignerRole> designerPR) {
        PartecipationRequest<DesignerRole> pr = getInnerDesignerRequest(designerPR);
        if (!this.getProjects().contains(pr.getProject()))
            throw new IllegalArgumentException("This program manager doesn't own the team!");
        pr.displayed("Congratulations! You are accepted.");
        pr.getProject().getTeam().getDesignerRequest().remove(pr);
        designerPR.getPendingRole().enterProject(designerPR.getProject());
        pr.getProject().getTeam().addDesigner(designerPR.getPendingRole());
        pr.getPendingRole().notify(pr.getDescription());
    }

    public void removePR(PartecipationRequest<DesignerRole> designerPR, String description) {
        PartecipationRequest<DesignerRole> pr = getInnerDesignerRequest(designerPR);
        if (!this.getProjects().contains(pr.getProject()))
            throw new IllegalArgumentException("This program manager doesn't own the team");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("The desccription can't be empty!");
        pr.displayed(description);
        pr.getProject().getTeam().getDesignerRequest().remove(pr);
        pr.getPendingRole().notify(pr.getDescription());
    }

    public Set<Long> getIdDesigners(Project projectInput) {
        return getInnerProject(projectInput).getTeam().getDesigners().stream().map(r -> r.getIdUser()).collect(Collectors.toSet());
    }

    public void removeDesigner(User user, Project projectInput) {
        Project project = getInnerProject(projectInput);
        DesignerRole designer = getInnerDesignerInTeam(user, project);
        if (!project.getTeam().getDesigners().contains(designer))
            throw new IllegalArgumentException("This program manager is not in the team: [" + project.getId() + "]");
        if (project.getTeam().getProjectManager() != null && designer.getIdUser().equals(project.getTeam().getProjectManager().getIdUser()))
            throw new IllegalArgumentException("A designer that has the project manager role can't be removed!");
        project.getTeam().removeDesigner(designer);
        designer.getProjects().remove(project);
        designer.notify("You are kicked out from project: ["+project.getName()+"]");
    }

    public void setProjectManager(User user, Project projectInput) {
        Project project = getInnerProject(projectInput);
        getInnerDesignerInTeam(user, projectInput);
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("This user doesn't own the project: [" + project.getId() + "]");
        if (!user.getRolesHandler().isProjectManager())
            user.getRolesHandler().addRole(ProjectManagerRole.TYPE, project.getCategory());
        ProjectManagerRole pj = user.getRolesHandler().getProjectManagerRole();
        project.getTeam().setProjectManager(pj);
        pj.enterProject(project);
        project.getTeam().getDesigners().remove(user.getRolesHandler().getDesignerRole());
        user.getRolesHandler().getDesignerRole().getProjects().remove(project);
        user.getRolesHandler().getProjectManagerRole().notify("You are the project manager of: [" + project.getName() + "]");
    }

    public Set<PartecipationRequest<DesignerRole>> getPartecipationRequestsByProject(Project projectInput) {
        Project project = getInnerProject(projectInput);
        if (!getProjects().contains(project))
            throw new IllegalArgumentException("This team is not owned by: [" + project.getId() + "]");
        return project.getTeam().getDesignerRequest();
    }

    public void openRegistrations(Project projectInput) {
        getInnerProject(projectInput).getTeam().openRegistrations();
    }

    public void closeRegistrations(Project projectInput) {
        getInnerProject(projectInput).getTeam().closeRegistrations();
    }

    @Override
    public PartecipationRequest<ProgramManagerRole> createPartecipationRequest(Project project) {
        getInnerCategory(project.getCategory());
        if (project.getTeam().getProgramManagerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("You already sent a partecipation request to this team!");
        if (project.getTeam().getProgramManager() != null)
            throw new IllegalArgumentException("This program manager is already in the team!");
        if (!this.getCategories().contains(project.getCategory()))
            throw new IllegalArgumentException("This user doesn't have the category: [" + project.getCategory() + "]");
        PartecipationRequest<ProgramManagerRole> pr = new PartecipationRequest(this, project);
        if (this.myPartecipationRequests.contains(pr))
            this.myPartecipationRequests.remove(pr);
        this.myPartecipationRequests.add(pr);
        if (project.getTeam().getProgramManagerRequest().contains(pr))
            project.getTeam().getProgramManagerRequest().remove(pr);
        project.getTeam().getProgramManagerRequest().add(pr);
        project.getTeam().getProjectProposer().notify("Someone sent a partecipation request: [" +
                project.getName() + "]");
        return pr;
    }

    @Override
    public void removeMyPr(PartecipationRequest<ProgramManagerRole> pr) {
        this.myPartecipationRequests.remove(pr);
        pr.getProject().getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<Project> projectsByCategory(Iterator<Project> iterator, Category category) {
        Set<Project> projects = new HashSet<>();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            if (project.getCategory().equals(category) && project.getTeam().getProgramManager() == null &&
                    !project.getTeam().getProgramManagerRequest().stream().map(r -> r.getPendingRole()).collect(Collectors.toSet()).contains(this))
                projects.add(project);
        }
        return projects;
    }

    public Set<PartecipationRequest<ProgramManagerRole>> myPartecipationRequests() {
        return this.myPartecipationRequests;
    }

}
