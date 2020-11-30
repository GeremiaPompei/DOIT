package it.unicam.cs.ids.DOIT;

public class Project {

    private int id;
    private String name;
    private String description;
    private User projectManager;
    private Team team;

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

    public void initTeam(User user) {
        this.team = new Team(user);
        user.getGestoreRuoli().getProgramManager().getTeams().add(this.team);
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        if(this.team.getDesigners().contains(projectManager))
            this.projectManager = projectManager;
    }
}
