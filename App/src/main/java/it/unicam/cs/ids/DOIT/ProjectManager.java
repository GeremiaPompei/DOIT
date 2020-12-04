package it.unicam.cs.ids.DOIT;

public class ProjectManager extends Designer{
    public ProjectManager(Designer designer) {
        super(designer.getCv());
    }

    public void changeProjectState(Project project, ProjectState projectState) throws Exception {
        if(project.getProjectManager().equals(this.getUser())) {
            project.setProjectState(projectState,this.getUser());
        }
    }


}
