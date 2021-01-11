package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;
import java.util.function.Function;

public class ProjectManagerRole extends Role {

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
    }

    public Function<Set<ProjectState>, ProjectState> upgradeState(Project project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return ps -> ps.stream().filter(p -> p.getId() == project.getId() + 1).findAny().orElse(null);
    }

    public Function<Set<ProjectState>, ProjectState> downgradeState(Project project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return ps -> ps.stream().filter(p -> p.getId() == project.getId() - 1).findAny().orElse(null);
    }
}