package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.IProjectState;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;

import java.util.Set;
import java.util.function.Function;

public interface IProjectManagerRole extends IRole {
    Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception;

    Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception;

    void insertEvaluation(IUser user, int evaluation, IProject project) throws RoleException;

    Set<IUser> getDesigners(ITeam team);

    void exitAll(IProject project) throws RoleException;
}
