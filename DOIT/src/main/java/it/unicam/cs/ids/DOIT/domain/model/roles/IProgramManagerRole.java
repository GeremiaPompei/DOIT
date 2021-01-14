package it.unicam.cs.ids.DOIT.domain.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;

import java.util.Set;

public interface IProgramManagerRole extends IRole {
    void addDesigner(IPartecipationRequest partecipationRequest) throws RoleException;

    void removePartecipationRequest(IPartecipationRequest partecipationRequest, String description);

    Set<IUser> getDesigners(ITeam team);

    void removeDesigner(IUser designer, ITeam team) throws RoleException;

    void setProjectManager(IUser designer, IProject project, Class<? extends IRole> clazz) throws ReflectiveOperationException, RoleException;

    ITeam createTeam(IProject project, IFactoryModel factory);

    Set<IPartecipationRequest> getPartecipationRequests(ITeam team);

    Set<ITeam> getTeams();

    void openRegistrations(ITeam team);

    void closeRegistrations(ITeam team);
}
