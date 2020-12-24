package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.ProgramManagerRole;

import java.util.Set;

public class ControllerChooseTeamMembers {
    private User user;

    public Set<Team> getTeams() throws RoleException {
        return this.user.getRole(ProgramManagerRole.class).getTeams();
    }

    public Set<PartecipationRequest> getPartecipationRequests(Team team) {
        return team.getPartecipationRequests();
    }

    public boolean addDesigner(PartecipationRequest partecipationRequest) throws RoleException {
        return this.user.getRole(ProgramManagerRole.class).addDesigner(partecipationRequest);
    }

    public boolean removePartecipationRequest(PartecipationRequest partecipationRequest, String description)
            throws RoleException {
        return this.user.getRole(ProgramManagerRole.class).removePartecipationRequest(partecipationRequest, description);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
