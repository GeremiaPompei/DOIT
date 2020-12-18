package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;

public class ControllerChooseProgramManager {
    private User user;

    public void becomeProgramManager(int idTeam, Project project) throws RoleException, ReflectiveOperationException {
        this.user.getRole(ProjectProposerRole.class).becomeProgramManager(project.getCategory());
        this.user.getRole(ProgramManagerRole.class).initTeam(idTeam, project);
    }

    public Set<User> findProgramManagerList(Category category) {
        return Database.searchUser(ProgramManagerRole.class, category);
    }

    public void itIsProgramManager(User user, int idTeam, Project project) throws RoleException {
        user.getRole(ProgramManagerRole.class).initTeam(idTeam, project);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
