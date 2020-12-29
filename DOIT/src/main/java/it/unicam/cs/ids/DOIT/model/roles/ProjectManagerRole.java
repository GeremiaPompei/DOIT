package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Arrays;

public class ProjectManagerRole extends Role {

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
    }

    public void upgradeState(Project project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        project.setProjectState(Arrays.stream(ProjectState.values()).filter(p -> p.getNum() == project.getProjectState()
                .getNum() + 1).findAny().orElseThrow(() -> new Exception()));
    }
}