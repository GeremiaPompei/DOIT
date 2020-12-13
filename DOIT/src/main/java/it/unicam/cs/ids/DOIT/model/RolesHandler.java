package it.unicam.cs.ids.DOIT.model;

public class RolesHandler {
    private User user;
    private ProgramManager programManager;

    public User getUser() {
        return user;
    }

    public ProgramManager getProgramManager() throws RoleException {
        if (this.programManager == null)
            throw new RoleException();
        return this.programManager;
    }

    public void initProgramManager() {
        this.programManager = new ProgramManager(this.user);
    }

}
