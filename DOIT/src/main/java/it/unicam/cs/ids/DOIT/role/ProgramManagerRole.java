package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends Role implements IPartecipationRequestHandler, IPendingRole {
    private Set<IPartecipationRequest> partecipationRequests;

    public ProgramManagerRole(IUser user, ICategory category) {
        super(user, category);
    }

    public void acceptPR(int idDesigner, int idProject) throws RoleException {
        IPartecipationRequest pr = getInnerPR(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getDesignerRequest().remove(pr);
        DesignerRole designer = pr.getPendingRole().getUser().getRole(DesignerRole.class);
        pr.getTeam().addDesigner(designer);
    }

    public void removePR(int idDesigner, int idProject, String description) {
        IPartecipationRequest pr = getInnerPR(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getDesignerRequest().remove(pr);
    }

    public Set<IUser> getDesigners(int idProject) {
        return getInnerTeam(idProject).getDesigners().stream().map(r -> r.getUser()).collect(Collectors.toSet());
    }

    public void removeDesigner(int idDesigner, int idProject) {
        ITeam team = getInnerTeam(idProject);
        DesignerRole designer = getInnerDesignerInTeam(idDesigner, idProject);
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getId() + "]");
        if (team.getProjectManager() != null && designer.getUser().equals(team.getProjectManager().getUser()))
            throw new IllegalArgumentException("Non può essere eliminato il designer che è project manager!");
        team.removeDesigner(designer);
    }

    public void setProjectManager(int idDesigner, int idProject) throws ReflectiveOperationException, RoleException {
        ITeam team = getInnerTeam(idProject);
        IUser user = getInnerDesignerInTeam(idDesigner, idProject).getUser();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + team.getId() + "]");
        user.addRole(ProjectManagerRole.class, team.getProject().getCategory().getName());
        team.setProjectManager(user.getRole(ProjectManagerRole.class));
        user.getRole(ProjectManagerRole.class).addCategory(team.getProject().getCategory().getName());
        user.getRole(ProjectManagerRole.class).enterTeam(team.getId());
    }

    public Set<IPartecipationRequest> getPartecipationRequestsByTeam(int idProject) {
        ITeam team = getInnerTeam(idProject);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getDesignerRequest();
    }

    public void openRegistrations(int idProject) {
        getInnerTeam(idProject).openRegistrations();
    }

    public void closeRegistrations(int idProject) {
        getInnerTeam(idProject).closeRegistrations();
    }

    @Override
    public void createPartecipationRequest(int idProject) {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        getInnerCategory(team.getProject().getCategory().getName());
        if (team.getProgramManagerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getProgramManagerRequest() != null)
            throw new IllegalArgumentException("Program Manager gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        IPartecipationRequest pr = ServicesHandler.getInstance().getFactoryModel().createPartecipationRequest(this, team);
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (team.getProgramManagerRequest().contains(pr))
            team.getProgramManagerRequest().remove(pr);
        team.getProgramManagerRequest().add(pr);
    }

    public Set<IProject> getProjectsByCategory(String idCategory) {
        return ServicesHandler.getInstance().getResourceHandler().getProjectsByCategory(idCategory).stream()
                .filter(p -> p.getTeam().getProgramManager() == null).collect(Collectors.toSet());
    }

    public Set<IPartecipationRequest> getMyPartecipationRequests() {
        return this.partecipationRequests;
    }

    @Override
    public String toString() {
        return "ProgramManagerRole{" +
                "role=" + super.toString() +
                '}';
    }
}
