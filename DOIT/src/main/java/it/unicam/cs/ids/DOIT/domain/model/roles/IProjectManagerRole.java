package it.unicam.cs.ids.DOIT.domain.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;

import java.util.Set;
import java.util.function.Function;

public interface IProjectManagerRole extends IRole {
    Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception;

    Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception;

    void insertEvaluation(IUser user,  int evaluation, IProject project) throws RoleException;

    Set<IUser> getDesigners(ITeam team);

    void exitAll(IProject project) throws RoleException;
}
