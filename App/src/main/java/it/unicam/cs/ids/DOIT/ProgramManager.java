package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager implements IRole {
    private User user;
    private List<Team> teams;

    public ProgramManager() {
        this.teams = new ArrayList<>();
    }

    public void accept(PartecipationRequest pr) {
        Team team = searchTeam(pr);
        if(team != null) {
            team.getPartecipationRequestList().remove(pr);
            team.getDesigners().add(pr.getUser());
        }
    }

    public void remove(PartecipationRequest pr) {
        Team team = searchTeam(pr);
        if(team != null) {
            team.getPartecipationRequestList().remove(pr);
        }
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public List<Project> getProjects() {
        return null;
    }

    public List<Team> getTeams() {
        return teams;
    }

    private Team searchTeam(PartecipationRequest pr) {
        return this.teams.stream()
                .filter(t -> t.getPartecipationRequestList()
                        .contains(pr)).findAny().get();
    }
}
