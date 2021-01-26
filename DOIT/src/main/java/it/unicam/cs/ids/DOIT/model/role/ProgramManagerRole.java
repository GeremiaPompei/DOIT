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
public class ProgramManagerRole extends Role implements PendingRole, IPartecipationRequestHandler<DesignerRole> {

    @Id
    @Column(name = "ID_ProgramManager")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pendingRole")
    private Set<PartecipationRequest<ProgramManagerRole>> myPartecipationRequests;

    public ProgramManagerRole() {
    }

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
        this.myPartecipationRequests = new HashSet<>();
    }

    public void acceptPR(DesignerRole designer, Project projectInput) {
        PartecipationRequest pr = getInnerDesignerRequest(designer, projectInput);
        if (!this.getProjects().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getDesignerRequest().remove(pr);
        designer.enterProject(projectInput);
        pr.getTeam().addDesigner(designer);
    }

    public void removePR(DesignerRole designer, Project projectInput, String description) {
        PartecipationRequest pr = getInnerDesignerRequest(designer, projectInput);
        if (!this.getProjects().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getDesignerRequest().remove(pr);
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
        Long token = user.getTokenHandler().getToken();
        user.getRolesHandler(token).addProjectManagerRole(project.getCategory());
        project.getTeam().setProjectManager(user.getRolesHandler(token).getProjectManagerRole());
        user.getRolesHandler(token).getProjectManagerRole().addCategory(project.getCategory());
        user.getRolesHandler(token).getProjectManagerRole().enterProject(project);
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
        PartecipationRequest<ProgramManagerRole> pr = new PartecipationRequest(this, project.getTeam());
        if (this.myPartecipationRequests.contains(pr))
            this.myPartecipationRequests.remove(pr);
        this.myPartecipationRequests.add(pr);
        if (project.getTeam().getProgramManagerRequest().contains(pr))
            project.getTeam().getProgramManagerRequest().remove(pr);
        project.getTeam().getProgramManagerRequest().add(pr);
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
