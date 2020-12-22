package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.Category;
import it.unicam.cs.ids.DOIT.model.Project;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.model.RoleException;
import it.unicam.cs.ids.DOIT.model.User;

public class ControllerInsertProposal {
    private User user;

    public Project createProject(int idProject, String name, String description, Category category)
            throws RoleException {
        return user.getRole(ProjectProposerRole.class).createProject(idProject, name, description, category);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

