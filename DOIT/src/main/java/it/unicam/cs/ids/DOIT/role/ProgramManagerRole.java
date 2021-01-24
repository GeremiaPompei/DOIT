package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends PendingRole implements IPartecipationRequestHandler {

    private ServicesHandler servicesHandler = ServicesHandler.getInstance();
    private Set<PartecipationRequest> partecipationRequests;

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
    }

    public void acceptPR(Long idDesigner, Long idProject) throws RoleException {
        PartecipationRequest pr = getInnerDesignerRequest(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getDesignerRequest().remove(pr);
        DesignerRole designer = pr.getPendingRole().getUser().getRole(DesignerRole.class);
        pr.getTeam().addDesigner(designer);
    }

    public void removePR(Long idDesigner, Long idProject, String description) {
        PartecipationRequest pr = getInnerDesignerRequest(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getDesignerRequest().remove(pr);
    }

    public Set<User> getDesigners(Long idProject) {
        return getInnerTeam(idProject).getDesigners().stream().map(r -> r.getUser()).collect(Collectors.toSet());
    }

    public void removeDesigner(Long idDesigner, Long idProject) {
        Team team = getInnerTeam(idProject);
        DesignerRole designer = getInnerDesignerInTeam(idDesigner, idProject);
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getId() + "]");
        if (team.getProjectManager() != null && designer.getUser().equals(team.getProjectManager().getUser()))
            throw new IllegalArgumentException("Non può essere eliminato il designer che è project manager!");
        team.removeDesigner(designer);
    }

    public void setProjectManager(Long idDesigner, Long idProject) throws ReflectiveOperationException, RoleException {
        Team team = getInnerTeam(idProject);
        User user = getInnerDesignerInTeam(idDesigner, idProject).getUser();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + team.getId() + "]");
        user.addRole(ProjectManagerRole.class, team.getProject().getCategory().getName());
        team.setProjectManager(user.getRole(ProjectManagerRole.class));
        user.getRole(ProjectManagerRole.class).addCategory(team.getProject().getCategory().getName());
        user.getRole(ProjectManagerRole.class).enterTeam(team.getId());
    }

    public Set<PartecipationRequest> getPartecipationRequestsByTeam(Long idProject) {
        Team team = getInnerTeam(idProject);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getDesignerRequest();
    }

    public void openRegistrations(Long idProject) {
        getInnerTeam(idProject).openRegistrations();
    }

    public void closeRegistrations(Long idProject) {
        getInnerTeam(idProject).closeRegistrations();
    }

    @Override
    public void createPartecipationRequest(Long idProject) {
        Team team = servicesHandler.getResourceHandler().getProject(idProject).getTeam();
        getInnerCategory(team.getProject().getCategory().getName());
        if (team.getProgramManagerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getProgramManager() != null)
            throw new IllegalArgumentException("Program Manager gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        PartecipationRequest pr = servicesHandler.getFactoryModel().createPartecipationRequest(this, team);
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (team.getProgramManagerRequest().contains(pr))
            team.getProgramManagerRequest().remove(pr);
        team.getProgramManagerRequest().add(pr);
    }

    public Set<Project> getProjectsByCategory(String idCategory) {
        return servicesHandler.getResourceHandler().getProjectsByCategory(idCategory).stream()
                .filter(p -> p.getTeam().getProgramManager() == null).collect(Collectors.toSet());
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
