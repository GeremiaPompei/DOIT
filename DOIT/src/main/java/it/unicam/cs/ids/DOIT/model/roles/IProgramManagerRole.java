package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;

public interface IProgramManagerRole extends IRole {
    void addDesigner(IPartecipationRequest partecipationRequest) throws RoleException;
    void removePartecipationRequest(IPartecipationRequest partecipationRequest, String description);
    Set<IUser> getDesigners(ITeam team);
    void removeDesigner(IUser designer, ITeam team) throws RoleException;
    void setProjectManager(IUser designer, IProject project);
    ITeam createTeam(IProject project, IFactory factory);
    Set<IPartecipationRequest> getPartecipationRequests(ITeam team);
    Set<ITeam> getTeams();
}
