package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager extends Role {
    public RolesHandler rolesHandler;
    public List<Team> teams = new ArrayList<>();

    public ProgramManager(User user) {
        super(user);
    }

    public boolean addDesigner() {
        throw new UnsupportedOperationException();
    }

    public boolean removeDesigner() {
        throw new UnsupportedOperationException();
    }

    public void setProjectManager(User user, Project project) throws RoleException {
        project.setProjectManager(user);
    }

    public void initTeam(int id, Project project) {
        Team team = new Team(id, project, super.getUser());
        this.teams.add(team);
        project.setTeam(team);
    }

    public void removePartecipationRequest(PartecipationRequest partecipationRequest) {
        throw new UnsupportedOperationException();
    }

    public Team getTeam(int id) {
        return teams.stream().filter(t -> t.getId() == id).findAny().orElse(null);
    }

    public List<Team> getTeams() {
        return teams;
    }
}