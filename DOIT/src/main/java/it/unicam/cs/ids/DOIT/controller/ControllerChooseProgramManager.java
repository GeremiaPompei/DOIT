package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.view.PredicateException;

public class ControllerChooseProgramManager {
    private User user;

    public void becomeProgramManager(Project project) throws RoleException, ReflectiveOperationException {
        this.user.getRole(ProjectProposerRole.class).becomeProgramManager(project.getCategory());
        this.user.getRole(ProgramManagerRole.class).initTeam(project);
    }

    public PredicateException<User> findProgramManagerList(Category category) {
        return u-> u.getRole(ProgramManagerRole.class).getCategories().contains(category);
    }

    public void initTeam(Project project) throws RoleException {
        this.user.getRole(ProgramManagerRole.class).initTeam(project);
    }

    public void removePartecipationRequest(PartecipationRequest partecipationRequest, String description) throws RoleException {
        this.user.getRole(ProgramManagerRole.class).removePartecipationRequest(partecipationRequest, description);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
