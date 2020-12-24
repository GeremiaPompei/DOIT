package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.HashSet;
import java.util.Set;

public class ProgramManagerRole extends Role {

    public Set<Team> teams = new HashSet<>();

    public ProgramManagerRole(User user, Category category) {
        super(user, category);
    }

    public boolean removePartecipationRequest(PartecipationRequest partecipationRequest, String description) {
        Team team = partecipationRequest.getTeam();
        if (this.teams.contains(team) && description != null && !description.equals("")) {
            partecipationRequest.displayed(description);
            return team.getPartecipationRequests().remove(partecipationRequest);
        }
        return false;
    }

    public boolean addDesigner(PartecipationRequest partecipationRequest) throws RoleException {
        Team team = partecipationRequest.getTeam();
        if (this.teams.contains(team)) {
            partecipationRequest.displayed("Congratulations! You are accepted.");
            boolean b = team.getPartecipationRequests().remove(partecipationRequest);
            return team.addDesigner(partecipationRequest.getDesigner()) && b;
        }
        return false;
    }

    public boolean removeDesigner(User designer, Team team) throws RoleException {
        if (this.teams.contains(team) && team.getDesigners().contains(designer))
            return team.removeDesigner(designer);
        return false;
    }

    public void setProjectManager(User user, Project project) throws RoleException {
        checkProject(project);
        if (!project.getTeam().getDesigners().contains(user))
            throw new IllegalArgumentException("L'utente non è presente nel team del progetto!");
        project.setProjectManager(user);
    }

    public void initTeam(Project project) {
        Team team = new Team(project, super.getUser());
        this.teams.add(team);
        project.setTeam(team);
        this.getProjects().add(project);
    }

    public Team getTeam(int id) {
        return teams.stream().filter(t -> t.getProject().getId() == id).findAny().orElse(null);
    }

    public Set<Team> getTeams() {
        return teams;
    }

    private void checkProject(Project project) {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("L'utente non possiede il progetto con id:[" + project.getId() + "]");
    }
}