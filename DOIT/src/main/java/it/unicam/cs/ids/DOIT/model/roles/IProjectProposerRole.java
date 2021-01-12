package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.function.Predicate;

public interface IProjectProposerRole extends IRole {
    IProject createProject(int id, String name, String description, ICategory category, IFactoryModel factory);

    Predicate<IUser> findProgramManagerList(ICategory category);

    ITeam createTeam(IUser user, IProject project, IFactoryModel factory) throws RoleException;
}
