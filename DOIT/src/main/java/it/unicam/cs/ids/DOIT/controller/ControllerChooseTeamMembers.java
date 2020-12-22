package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;

import java.util.List;

public class ControllerChooseTeamMembers {
    private User user;

    public List<Team> getTeams() throws RoleException {
        return this.user.getRole(ProgramManagerRole.class).getTeams();
    }

    public List<PartecipationRequest> getPartecipationRequests(Team team) throws RoleException {
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
