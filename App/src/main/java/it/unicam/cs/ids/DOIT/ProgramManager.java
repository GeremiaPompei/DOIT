package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProgramManager implements IRole {
    private User user;
    private List<Team> teams;

    public List<PartecipationRequest> getPartecipationRequest(Team team) {
        return searchTeam(t -> team.equals(t)).getPartecipationRequestList();
    }

    public ProgramManager() {
        this.teams = new ArrayList<>();
    }

    public void accept(PartecipationRequest pr) {
        Team team = searchTeam(t -> t.getPartecipationRequestList().contains(pr));
        if(team != null) {
            team.getPartecipationRequestList().remove(pr);
            team.getDesigners().add(pr.getUser());
        }
    }

    public void remove(PartecipationRequest pr, String reason) {
        if(!reason.isBlank()) {
            Team team = searchTeam(t -> t.getPartecipationRequestList().contains(pr));
            if (team != null) {
                team.getPartecipationRequestList().remove(pr);
                pr.setDescription(reason);
            }
        }
    }

    public void setProjectManager(Project project, User projectManager) {
        Team team = searchTeam(t -> t.getProject().equals(project));
        if(team != null) {
            team.getProject().setProjectManager(projectManager);
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

    private Team searchTeam(Predicate<Team> p) {
        return this.teams.stream().filter(p).findAny().get();
    }
}
