package it.unicam.cs.ids.DOIT.model.roles.initial;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramManagerRole extends Role {

    public Set<Team> teams = new HashSet<>();

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
    }

    public void addDesigner(PartecipationRequest partecipationRequest) throws RoleException {
        Team team = partecipationRequest.getTeam();
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        partecipationRequest.displayed("Congratulations! You are accepted.");
        team.getPartecipationRequests().remove(partecipationRequest);
        team.addDesigner(partecipationRequest.getDesigner());
    }

    public void removePartecipationRequest(PartecipationRequest partecipationRequest, String description) {
        Team team = partecipationRequest.getTeam();
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        partecipationRequest.displayed(description);
        team.getPartecipationRequests().remove(partecipationRequest);
    }

    public Set<User> getDesigners(Team team) {
        if (!teams.contains(team))
            throw new IllegalArgumentException("Team non presente: [" + team.getProject().getId() + "]");
        return team.getDesigners();
    }

    public void removeDesigner(User designer, Team team) throws RoleException {
        if (!this.teams.contains(team))
            throw new IllegalArgumentException("Il Program Manager non possiede il team: [" + team.getProject().getId()
                    + "]");
        if (!team.getDesigners().contains(designer))
            throw new IllegalArgumentException("Il Program Manager non è interno al team: [" + team.getProject().getId()
                    + "]");
        team.removeDesigner(designer);
    }

    public void setProjectManager(User designer, Project project) {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
        if (!project.getTeam().getDesigners().contains(designer))
            throw new IllegalArgumentException("L'utente non è presente nel team del progetto!");
        project.setProjectManager(designer);
    }

    public Team createTeam(Project project) {
        Team team = new Team(project, super.getUser());
        this.teams.add(team);
        project.setTeam(team);
        this.addProject(project);
        return team;
    }

    public Set<PartecipationRequest> getPartecipationRequests(Team team) {
        if (!teams.contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getProject().getId() + "]");
        return team.getPartecipationRequests();
    }

    public Set<Team> getTeams() {
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