package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.ITeam;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

public class Project implements IProject {
    private int id;
    private String name;
    private String description;
    private ProjectState projectState;
    private ITeam team;
    private ICategory category;

    public Project(int id, String name, String description, ICategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.projectState = ServicesHandler.getInstance().getResourceHandler().getProjectState(0);
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

    public ITeam getTeam() {
        return team;
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

    public ICategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectState=" + projectState +
                ", team=" + team +
                '}';
    }
}