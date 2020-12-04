package it.unicam.cs.ids.DOIT;

import java.util.List;

public class GestoreRuoli {
    private User user;
    private Designer designer;
    private ProjectProposer projectProposer;
    private ProgramManager programManager;
    private ProjectManager projectManager;
    private List<IRole> roles;

    public List<IRole> getRoles() {
        return roles;
    }

    public GestoreRuoli(User user) {
        this.user = user;
    }

    public Designer getDesigner() throws NullPointerException {
        return designer;
    }

    public ProjectProposer getProjectProposer() throws NullPointerException {
        return projectProposer;
    }

    public ProgramManager getProgramManager() throws NullPointerException {
        return programManager;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void initDesigner(List<String> cv) {
        this.designer = new Designer(cv);
        initRole(this.designer);
    }

    public void initProjectProposer() {
        this.projectProposer = new ProjectProposer();
        initRole(this.projectProposer);
    }

    public void initProgramManager() {
        this.programManager = new ProgramManager();
        initRole(this.programManager);
    }

    private void initRole(IRole var) {
        var.setUser(this.user);
        this.roles.add(var);
    }
}
