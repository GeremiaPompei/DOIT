package it.unicam.cs.ids.DOIT;

public class Project {

    private int id;
    private String name;
    private String description;
    private User projectManager;
    private Team team;
    private ProjectState projectState;

    public Team getTeam() {
        return team;
    }

    public void setProjectState(ProjectState projectState, User pm) throws Exception {
        if(pm.equals(this.projectManager))
            this.projectState = projectState;
        else
            throw new Exception("Non sei Project Manager!!!");
    }

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

    public void setTeam(Team team) throws RoleException {
        this.team = team;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        if(this.team.getDesigners().contains(projectManager))
            this.projectManager = projectManager;
    }
}
