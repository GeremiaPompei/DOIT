package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.List;

public class ControllerChooseProgramManager {
    private User user;

    public void becomeProgramManager(int idTeam, Project project) throws RoleException, ReflectiveOperationException {
        this.user.getRole(ProjectProposerRole.class).becomeProgramManager();
        this.user.getRole(ProgramManagerRole.class).initTeam(idTeam, project);
    }

    public List<User> findProgramManagerList(Category category) {
        return this.user.searchUser(ProgramManagerRole.class, category);
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
