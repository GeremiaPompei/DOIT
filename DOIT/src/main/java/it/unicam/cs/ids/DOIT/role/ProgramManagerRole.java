package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends Role implements PartecipationRequestHandler {

    public ProgramManagerRole(int user, String category) {
        super(user, category);
    }

    public void acceptPR(int idDesigner, int idProject) throws RoleException {
        IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getPR(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getDesignerRequest().remove(pr);
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(pr.getPendingRole().getId());
        pr.getTeam().addDesigner(user.getRole(DesignerRole.class));
    }

    public void removePR(int idDesigner, int idProject, String description) {
        IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getPR(idDesigner, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Program Manager non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getDesignerRequest().remove(pr);
    }

    public Set<IUser> getDesigners(int idProject) {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non presente: [" + team.getId() + "]");
        return team.getDesigners().stream().map(r -> ServicesHandler.getInstance().getResourceHandler()
                .getUser(r.getId())).collect(Collectors.toSet());
    }

    public void removeDesigner(int idDesigner, int idProject) throws RoleException {
        DesignerRole designer = ServicesHandler.getInstance().getResourceHandler().getUser(idDesigner).getRole(DesignerRole.class);
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getId()
                    + "]");
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getId()
                    + "]");
        team.removeDesigner(designer);
    }

    public void setProjectManager(int idDesigner, int idProject) throws ReflectiveOperationException, RoleException {
        IUser designer = ServicesHandler.getInstance().getResourceHandler().getUser(idDesigner);
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + team.getId() + "]");
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("L'utente non è presente nel team del progetto!");
        designer.addRole(ProjectManagerRole.class, team.getProject().getCategory().getName());
        team.setProjectManager(designer.getRole(ProjectManagerRole.class));
        designer.getRole(ProjectManagerRole.class).addCategory(team.getProject().getCategory().getName());
        designer.getRole(ProjectManagerRole.class).enterTeam(team.getId());
    }

    public Set<IPartecipationRequest> getPartecipationRequests(int idProject) {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getDesignerRequest();
    }

    public void openRegistrations(int idProject) {
        ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam().openRegistrations();
    }

    public void closeRegistrations(int idProject) {
        ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam().closeRegistrations();
    }

    @Override
    public String toString() {
        return "ProgramManagerRole{" +
                "role=" + super.toString() +
                '}';
    }
}
