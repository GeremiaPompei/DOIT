package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;
import java.util.function.Function;

public class ProjectManagerRole extends Role implements IProjectManagerRole {

    public ProjectManagerRole(IUser user, ICategory category) {
        super(user, category);
    }

    public Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return ps -> ps.stream().filter(p -> p.getId() == project.getId() + 1).findAny().orElse(null);
    }

    public Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return ps -> ps.stream().filter(p -> p.getId() == project.getId() - 1).findAny().orElse(null);
    }
}