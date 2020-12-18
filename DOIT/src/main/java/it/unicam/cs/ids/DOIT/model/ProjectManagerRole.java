package it.unicam.cs.ids.DOIT.model;

import java.util.Arrays;

public class ProjectManagerRole extends Role {

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
    }

    public boolean changeState(ProjectState state) {
        throw new UnsupportedOperationException();
    }

    public void upgradeState(Project project) throws Exception {
        project.setProjectState(Arrays.stream(ProjectState.values()).filter(p -> p.getNum() == project.getProjectState()
                .getNum() + 1).findAny().orElseThrow(() -> new Exception()));
    }
}