package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.role.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.role.IUser;
import it.unicam.cs.ids.DOIT.role.RoleException;

import java.util.Set;

public interface ITeam {
    boolean getState();

    IProject getProject();

    IUser getProgramManager();

    void addDesigner(IUser designer) throws RoleException;

    void removeDesigner(IUser designer) throws RoleException;

    Set<IUser> getDesigners();

    Set<IPartecipationRequest> getPartecipationRequests();

    void openRegistrations();

    void closeRegistrations();

}
