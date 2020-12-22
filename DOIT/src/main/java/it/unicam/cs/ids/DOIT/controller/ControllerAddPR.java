package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.DesignerRole;
import it.unicam.cs.ids.DOIT.view.PredicateException;


public class ControllerAddPR {
    private User user;

    public PredicateException<Project> getSetOfProjects(Category category) throws RoleException {
        return p -> user.getRole(DesignerRole.class).getCategories().contains(category) &&
                p.getCategory().equals(category);

    }

    public PredicateException<Project> getProjectFromSet(int id) {
        return p -> p.getId() == id;
    }

    public void addPartecipationRequest(Project project) throws RoleException {
        user.getRole(DesignerRole.class).createPartecipationRequest(project.getTeam());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}