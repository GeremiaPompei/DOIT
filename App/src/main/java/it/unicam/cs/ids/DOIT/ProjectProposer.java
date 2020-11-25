package it.unicam.cs.ids.DOIT;

public class ProjectProposer {

    public Project createProject(int id, String name, String description) {
        return new Project(id,name,description);
    }
}
