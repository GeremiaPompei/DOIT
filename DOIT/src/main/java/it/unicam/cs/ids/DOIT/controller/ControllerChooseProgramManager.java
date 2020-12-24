package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.ProgramManagerRole;

import java.util.function.Predicate;

public class ControllerChooseProgramManager {
    private User user;

    public Predicate<User> findProgramManagerList(Category category) {
        return u -> {
            try {
                return u.getRole(ProgramManagerRole.class).getCategories().contains(category);
            } catch (RoleException e) {
                return false;
            }
        };
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
