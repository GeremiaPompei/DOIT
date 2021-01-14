package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;

import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends Role implements IProgramManagerRole {

    private IFactoryModel factoryModel;

    public ProgramManagerRole(IUser user, ICategory category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
        this.factoryModel = factoryModel;
    }

    public void addDesigner(IPartecipationRequest partecipationRequest) throws RoleException {
        ITeam team = partecipationRequest.getTeam();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        partecipationRequest.displayed("Congratulations! You are accepted.");
        team.getPartecipationRequests().remove(partecipationRequest);
        team.addDesigner(partecipationRequest.getUser());
    }

    public void removePartecipationRequest(IPartecipationRequest partecipationRequest, String description) {
        ITeam team = partecipationRequest.getTeam();
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        partecipationRequest.displayed(description);
        team.getPartecipationRequests().remove(partecipationRequest);
    }

    public Set<IUser> getDesigners(ITeam team) {
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non presente: [" + team.getProject().getId() + "]");
        return team.getDesigners();
    }

    public void removeDesigner(IUser designer, ITeam team) throws RoleException {
        if (!this.getTeams().contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getProject().getId()
                    + "]");
        team.removeDesigner(designer);
    }

    public void setProjectManager(IUser designer, IProject project, Class<? extends IRole> clazz) throws ReflectiveOperationException, RoleException {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
        if (!project.getTeam().getDesigners().contains(designer))
            throw new IllegalArgumentException("L'utente non è presente nel team del progetto!");
        project.setProjectManager(designer);
        designer.addRole(clazz, project.getCategory(), factoryModel);
        designer.getRole(IProjectManagerRole.class).addCategory(project.getCategory());
        designer.getRole(IProjectManagerRole.class).enterProject(project);
    }

    public ITeam createTeam(IProject project, IFactoryModel factory) {
        ITeam team = factory.createTeam(project, super.getUser());
        this.getTeams().add(team);
        project.setTeam(team);
        this.enterProject(project);
        return team;
    }

    public Set<IPartecipationRequest> getPartecipationRequests(ITeam team) {
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getProject().getId() + "]");
        return team.getPartecipationRequests();
    }

    public void openRegistrations(ITeam team) {
        team.openRegistrations();
    }

    public void closeRegistrations(ITeam team) {
        team.closeRegistrations();
    }

    public Set<ITeam> getTeams() {
        return this.getProjects().stream().map(p -> p.getTeam()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "ProgramManagerRole{" +
                "role=" + super.toString() +
                ", teams=" + getTeams().stream().map(t -> t.getProject().getId()).collect(Collectors.toSet()) +
                '}';
    }

}