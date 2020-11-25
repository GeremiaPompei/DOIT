package it.unicam.cs.ids.DOIT;

public class Project {

    private int id;
    private String name;
    private String description;
    private User projectManager;

    public Project(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean setProjectManager(User projectManager) {
        if(projectManager.getGestoreRuoli().getDesigner()==null) {
            return false;
        }else {
            this.projectManager = projectManager;
            return true;
        }
    }

    public User getProjectManager() {
        return projectManager;
    }
}
