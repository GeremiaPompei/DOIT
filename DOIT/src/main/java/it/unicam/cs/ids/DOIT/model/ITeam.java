package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;

import java.util.Set;

public interface ITeam {
    boolean getState();

    IProject getProject();

    IUser getProgramManager();

    void addDesigner(IUser designer) throws RoleException;

    void removeDesigner(IUser designer) throws RoleException;

    Set<IUser> getDesigners();

    Set<IPartecipationRequest> getPartecipationRequests();

}
