package it.unicam.cs.ids.DOIT;

public class GestoreRuoli {
    private Designer designer;
    private ProjectProposer projectProposer;
    private ProgramManager programManager;

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
    }

    public void setProjectProposer(ProjectProposer projectProposer) {
        this.projectProposer = projectProposer;
    }

    public void setProgramManager(ProgramManager programManager) {
        this.programManager = programManager;
    }
}
