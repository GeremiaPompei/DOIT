package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.IProject;
import it.unicam.cs.ids.DOIT.model.IProjectState;
import it.unicam.cs.ids.DOIT.model.IRole;

import java.util.Set;
import java.util.function.Function;

public interface IProjectManagerRole extends IRole {
    Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception;

    Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception;
}
