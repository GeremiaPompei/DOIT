package it.unicam.cs.ids.DOIT.model;

public class Project {
    private int id;
    private String name;
    private String description;
    private User projectProposer;
    private Category category;
    private User projectManager;
    private ProjectState projectState;
    private Team team;

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public Project(int id, String name, String description, User projectProposer, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectProposer = projectProposer;
        this.category = category;
        this.projectState = ProjectState.INITIALIZATION;
        GestoreRisorse.getInstance().getRisorse().get(Project.class).add(this);
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean checkProjectManager(User projectManager) {
        throw new UnsupportedOperationException();
    }

    public Team getTeam() {
        return team;
    }

    public User getProjectManager() {
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

    public User getProjectProposer() {
        return projectProposer;
    }

    public Category getCategory() {
        return category;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
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