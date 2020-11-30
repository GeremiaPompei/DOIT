package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<PartecipationRequest> partecipationRequestList;
    private List<User> designers;
    private User programManager;
    private int id;

    public int getId() {
        return id;
    }

    public Team(User programManager) {
        partecipationRequestList = new ArrayList<>();
        this.programManager = programManager;
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
