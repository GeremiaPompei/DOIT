package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;

import java.util.function.Predicate;

public interface IProjectProposerRole extends IRole {
    IProject createProject(int id, String name, String description, ICategory category, IFactoryModel factory);

    Predicate<IUser> findProgramManagerList(ICategory category);

    ITeam createTeam(IUser user, IProject project, IFactoryModel factory) throws RoleException;
}
