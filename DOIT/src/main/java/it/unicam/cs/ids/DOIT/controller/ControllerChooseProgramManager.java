package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.view.PredicateException;

public class ControllerChooseProgramManager {
    private User user;

    public PredicateException<User> findProgramManagerList(Category category) {
        return u-> u.getRole(ProgramManagerRole.class).getCategories().contains(category);
    }

    public void initTeam(User user, Project project) throws RoleException {
        user.getRole(ProgramManagerRole.class).initTeam(project);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
