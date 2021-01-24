package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
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

    public ProgramManagerRole(User user, Category category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
        this.partecipationRequests = new HashSet<>();
    }

    public void acceptPR(DesignerRole designer, Team team) {
        PartecipationRequest pr = getInnerDesignerRequest(designer, team);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getDesignerRequest().remove(pr);
        pr.getTeam().addDesigner(designer);
    }

    public void removePR(DesignerRole designer, Team team, String description) {
        PartecipationRequest pr = getInnerDesignerRequest(designer, team);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getDesignerRequest().remove(pr);
    }

    public Set<User> getDesigners(Team team) {
        return getInnerTeam(team).getDesigners().stream().map(r -> r.getUser()).collect(Collectors.toSet());
    }

    public void removeDesigner(DesignerRole inputDesigner, Team inputTeam) {
        Team team = getInnerTeam(inputTeam);
        DesignerRole designer = getInnerDesignerInTeam(inputDesigner, team);
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getId() + "]");
        if (team.getProjectManager() != null && designer.getUser().equals(team.getProjectManager().getUser()))
            throw new IllegalArgumentException("Non può essere eliminato il designer che è project manager!");
        team.removeDesigner(designer);
    }

    public void setProjectManager(DesignerRole inputDesigner, Team inputTeam) throws ReflectiveOperationException, RoleException {
        Team team = getInnerTeam(inputTeam);
        User user = getInnerDesignerInTeam(inputDesigner, inputTeam).getUser();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + team.getId() + "]");
        user.getRolesHandler().addProjectManagerRole(team.getProject().getCategory());
        team.setProjectManager(user.getRolesHandler().getProjectManagerRole());
        user.getRolesHandler().getProjectManagerRole().addCategory(team.getProject().getCategory());
        user.getRolesHandler().getProjectManagerRole().enterTeam(team);
    }

    public Set<PartecipationRequest<DesignerRole>> getPartecipationRequestsByTeam(Team inputTeam) {
        Team team = getInnerTeam(inputTeam);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getDesignerRequest();
    }

    public void openRegistrations(Team team) {
        getInnerTeam(team).openRegistrations();
    }

    public void closeRegistrations(Team team) {
        getInnerTeam(team).closeRegistrations();
    }

    @Override
    public void createPartecipationRequest(Team team) {
        getInnerCategory(team.getProject().getCategory());
        if (team.getProgramManagerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getProgramManager() != null)
            throw new IllegalArgumentException("Program Manager gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        PartecipationRequest pr = factoryModel.createPartecipationRequest(this, team);
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (team.getProgramManagerRequest().contains(pr))
            team.getProgramManagerRequest().remove(pr);
        team.getProgramManagerRequest().add(pr);
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

    @Override
    public String toString() {
        return "ProgramManagerRole{" +
                "role=" + super.toString() +
                '}';
    }
}
