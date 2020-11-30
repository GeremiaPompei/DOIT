package it.unicam.cs.ids.DOIT;

import java.util.List;

public class GestoreRuoli {
    private User user;
    private Designer designer;
    private ProjectProposer projectProposer;
    private ProgramManager programManager;

    public List<IRole> getRoles() {
        return roles;
    }

    private List<IRole> roles;

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

    public void setDesigner(Designer designer) {
        this.designer = designer;
        this.designer.setUser(this.user);
        this.roles.add(this.designer);
    }

    public void setProjectProposer(ProjectProposer projectProposer) {
        this.projectProposer = projectProposer;
        this.projectProposer.setUser(this.user);
        this.roles.add(this.projectProposer);
    }

    public void setProgramManager(ProgramManager programManager) {
        this.programManager = programManager;
        this.programManager.setUser(this.user);
        this.roles.add(this.programManager);
    }
}
