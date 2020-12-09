package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Project project;
    private List<PartecipationRequest> partecipationRequestList;
    private List<User> designers;
    private User programManager;
    private int id;

    public Project getProject() {
        return project;
    }

    public int getId() {
        return id;
    }

    public Team(User programManager, Project project) {
        partecipationRequestList = new ArrayList<>();
        this.programManager = programManager;
        this.project = project;
    }

    public List<User> getDesigners() {
        return designers;
    }

    public User getProgramManager() {
        return programManager;
    }

    public List<PartecipationRequest> getPartecipationRequestList() {
        return partecipationRequestList;
    }
}
