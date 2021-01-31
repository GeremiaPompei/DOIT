package it.unicam.cs.ids.DOIT.model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProgramManagerRole extends Role implements IPendingRole, IPartecipationRequestHandler<DesignerRole> {

    public final static String TYPE = "program-manager";

    @Id
    @Column(name = "ID_ProgramManager")
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
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getProject().getTeam().getDesignerRequest().remove(pr);
        designerPR.getPendingRole().enterProject(designerPR.getProject());
        pr.getProject().getTeam().addDesigner(designerPR.getPendingRole());
        pr.getPendingRole().notify(pr.getDescription());
    }

    public void removePR(PartecipationRequest<DesignerRole> designerPR, String description) {
        PartecipationRequest<DesignerRole> pr = getInnerDesignerRequest(designerPR);
        if (!this.getProjects().contains(pr.getProject()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
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
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + project.getId() + "]");
        if (project.getTeam().getProjectManager() != null && designer.getIdUser().equals(project.getTeam().getProjectManager().getIdUser()))
            throw new IllegalArgumentException("Non può essere eliminato il designer che è project manager!");
        project.getTeam().removeDesigner(designer);
        designer.getProjects().remove(project);
    }

    public void setProjectManager(User user, Project projectInput) {
        Project project = getInnerProject(projectInput);
        getInnerDesignerInTeam(user, projectInput);
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
        user.rolesHandlerGet().addRole(ProjectManagerRole.TYPE, project.getCategory());
        ProjectManagerRole pj = user.rolesHandlerGet().getProjectManagerRole();
        project.getTeam().setProjectManager(pj);
        pj.enterProject(project);
        project.getTeam().getDesigners().remove(user.rolesHandlerGet().getDesignerRole());
        user.rolesHandlerGet().getDesignerRole().getProjects().remove(project);
        user.rolesHandlerGet().getProjectManagerRole().notify("You are project manager of: [" + project.getName() + "]");
    }

    public Set<PartecipationRequest<DesignerRole>> getPartecipationRequestsByProject(Project projectInput) {
        Project project = getInnerProject(projectInput);
        if (!getProjects().contains(project))
            throw new IllegalArgumentException("Team non posseduto: [" + project.getId() + "]");
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
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (project.getTeam().getProgramManager() != null)
            throw new IllegalArgumentException("Program Manager gia presente nel team!");
        if (!this.getCategories().contains(project.getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + project.getCategory() + "]");
        PartecipationRequest<ProgramManagerRole> pr = new PartecipationRequest(this, project);
        if (this.myPartecipationRequests.contains(pr))
            this.myPartecipationRequests.remove(pr);
        this.myPartecipationRequests.add(pr);
        if (project.getTeam().getProgramManagerRequest().contains(pr))
            project.getTeam().getProgramManagerRequest().remove(pr);
        project.getTeam().getProgramManagerRequest().add(pr);
        project.getTeam().getProjectProposer().notify("Qualcuno vuole partecipare al progetto: [" +
                project.getName() + "]");
        return pr;
    }

    public Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category) {
        Set<Project> projects = new HashSet<>();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            if (project.getCategory().equals(category) && project.getTeam().getProgramManager() == null)
                projects.add(project);
        }
        return projects;
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getMyPartecipationRequests() {
        return this.myPartecipationRequests;
    }

}
