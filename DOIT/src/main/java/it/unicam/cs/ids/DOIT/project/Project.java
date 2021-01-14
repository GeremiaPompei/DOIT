package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.IUser;

public class Project implements IProject {
    private int id;
    private String name;
    private String description;
    private IUser projectProposer;
    private IUser projectManager;
    private ICategory category;
    private IProjectState projectState;
    private ITeam team;

    public Project(int id, String name, String description, IUser projectProposer, ICategory category, IProjectState projectState) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectProposer = projectProposer;
        this.category = category;
        this.projectState = projectState;
    }

    public void setProjectManager(IUser projectManager) {
        this.projectManager = projectManager;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

    public ITeam getTeam() {
        return team;
    }

    public IUser getProjectManager() {
        return projectManager;
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

    public IUser getProjectProposer() {
        return projectProposer;
    }

    public ICategory getCategory() {
        return category;
    }

    public IProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(IProjectState projectState) {
        this.projectState = projectState;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectProposer=" + projectProposer.getId() +
                ", category=" + category +
                ", projectManager=" + (projectManager == null ? "null" : projectManager.getId()) +
                ", projectState=" + projectState +
                ", team=" + team +
                '}';
    }
}