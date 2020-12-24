package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.DesignerRole;

import java.util.function.Predicate;


public class ControllerAddPR {
    private User user;

    public Predicate<Project> getProjects(Category category) {
        return p -> {
            try {
                return user.getRole(DesignerRole.class).getCategories().contains(category) &&
                        p.getCategory().equals(category);
            } catch (RoleException e) {
                return false;
            }
        };
    }

    public Predicate<Project> getProject(int id) {
        return p -> p.getId() == id;
    }

    public boolean addPartecipationRequest(Project project) throws RoleException {
        return user.getRole(DesignerRole.class).createPartecipationRequest(project.getTeam());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}