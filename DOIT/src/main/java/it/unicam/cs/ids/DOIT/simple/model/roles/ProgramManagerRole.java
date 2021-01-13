package it.unicam.cs.ids.DOIT.simple.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.domain.model.roles.IProgramManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.Role;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProjectManagerRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends Role implements IProgramManagerRole {

    private Set<ITeam> teams;
    private IFactoryModel factoryModel;

    public ProgramManagerRole(IUser user, ICategory category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
        teams = new HashSet<>();
        this.factoryModel = factoryModel;
    }

    public void addDesigner(IPartecipationRequest partecipationRequest) throws RoleException {
        ITeam team = partecipationRequest.getTeam();
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        partecipationRequest.displayed("Congratulations! You are accepted.");
        team.getPartecipationRequests().remove(partecipationRequest);
        team.addDesigner(partecipationRequest.getUser());
    }

    public void removePartecipationRequest(IPartecipationRequest partecipationRequest, String description) {
        ITeam team = partecipationRequest.getTeam();
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        partecipationRequest.displayed(description);
        team.getPartecipationRequests().remove(partecipationRequest);
    }

    public Set<IUser> getDesigners(ITeam team) {
        if (!teams.contains(team))
            throw new IllegalArgumentException("Team non presente: [" + team.getProject().getId() + "]");
        return team.getDesigners();
    }

    public void removeDesigner(IUser designer, ITeam team) throws RoleException {
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getProject().getId()
                    + "]");
        team.removeDesigner(designer);
    }

    public void setProjectManager(IUser designer, IProject project, Class<? extends  IRole> clazz) throws ReflectiveOperationException {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
        if (!project.getTeam().getDesigners().contains(designer))
            throw new IllegalArgumentException("L'utente non è presente nel team del progetto!");
        project.setProjectManager(designer);
        designer.addRole(clazz, project.getCategory(), factoryModel);
    }

    public ITeam createTeam(IProject project, IFactoryModel factory) {
        ITeam team = factory.createTeam(project, super.getUser());
        this.teams.add(team);
        project.setTeam(team);
        this.enterProject(project);
        return team;
    }

    public Set<IPartecipationRequest> getPartecipationRequests(ITeam team) {
        if (!teams.contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getProject().getId() + "]");
        return team.getPartecipationRequests();
    }

    public Set<ITeam> getTeams() {
        return teams;
    }

    @Override
    public String toString() {
        return "ProgramManagerRole{" +
                "role=" + super.toString() +
                ", teams=" + teams.stream().map(t -> t.getProject().getId()).collect(Collectors.toSet()) +
                '}';
    }

}