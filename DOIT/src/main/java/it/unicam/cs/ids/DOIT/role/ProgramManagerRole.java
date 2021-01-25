package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProgramManagerRole extends PendingRole implements IPartecipationRequestHandler<DesignerRole> {

    @Id
    @Column(name = "ID_ProgramManager")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<PartecipationRequest> partecipationRequests;

    public ProgramManagerRole() {
        super();
    }

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
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

    //TODO controllare user
    public void setProjectManager(User user, Project projectInput) {
        Project project = getInnerProject(projectInput);
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
        user.getRolesHandler().addProjectManagerRole(project.getCategory());
        project.getTeam().setProjectManager(user.getRolesHandler().getProjectManagerRole());
        user.getRolesHandler().getProjectManagerRole().addCategory(project.getCategory());
        user.getRolesHandler().getProjectManagerRole().enterProject(project);
    }

    public Set<PartecipationRequest<DesignerRole>> getPartecipationRequestsByTeam(Project projectInput) {
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
    public void createPartecipationRequest(Project project) {
        getInnerCategory(project.getCategory());
        if (project.getTeam().getProgramManagerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (project.getTeam().getProgramManager() != null)
            throw new IllegalArgumentException("Program Manager gia presente nel team!");
        if (!this.getCategories().contains(project.getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + project.getCategory() + "]");
        PartecipationRequest pr = new PartecipationRequest(this, project.getTeam());
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (project.getTeam().getProgramManagerRequest().contains(pr))
            project.getTeam().getProgramManagerRequest().remove(pr);
        project.getTeam().getProgramManagerRequest().add(pr);
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

    public Set<PartecipationRequest> getMyPartecipationRequests() {
        return this.partecipationRequests;
    }

}
