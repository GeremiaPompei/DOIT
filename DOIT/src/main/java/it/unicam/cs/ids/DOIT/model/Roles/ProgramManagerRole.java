package it.unicam.cs.ids.DOIT.model.Roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramManagerRole extends Role {

    public List<Team> teams = new ArrayList<>();

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

    public List<Team> getTeams() {
        return teams;
    }
}