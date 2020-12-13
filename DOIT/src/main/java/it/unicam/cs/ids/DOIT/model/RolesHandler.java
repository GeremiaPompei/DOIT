package it.unicam.cs.ids.DOIT.model;

public class RolesHandler {
    private User user;
    private ProgramManager programManager;
    private ProjectManager projectManager;
    private ProjectProposer projectProposer;

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

    public ProjectManager getProjectManager() throws RoleException {
        if (this.projectManager == null)
            throw new RoleException();
        return this.projectManager;
    }

    public void initProjectManager() {
        this.projectManager = new ProjectManager(this.user);
    }

    public ProjectProposer getProjectProposer() throws RoleException {
        if (this.projectProposer == null)
            throw new RoleException();
        return this.projectProposer;
    }

    public void initProjectProposer() {
        this.projectProposer = new ProjectProposer(this.user);
    }
}
