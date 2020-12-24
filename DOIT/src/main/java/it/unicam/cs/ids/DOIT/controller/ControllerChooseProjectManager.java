package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.Project;
import it.unicam.cs.ids.DOIT.model.RoleException;
import it.unicam.cs.ids.DOIT.model.User;

import java.util.Set;

public class ControllerChooseProjectManager {

    private User user;

    public void becomeProjectManager(User designer, Project project) throws RoleException {
        this.user.getRole(ProgramManagerRole.class).setProjectManager(designer, project);
    }

    public Set<User> getDesigners(Project project) {
        return project.getTeam().getDesigners();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}