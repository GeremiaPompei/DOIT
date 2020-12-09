package it.unicam.cs.ids.DOIT;

import java.time.LocalDateTime;

public class PartecipationRequest {
    private LocalDateTime dateTime;
    private User user;
    private Team team;
    private String description;

    public PartecipationRequest(User user, Team team) throws RoleException {
        if(user.getGestoreRuoli().getDesigner() == null)
            throw new IllegalArgumentException();
        this.dateTime = LocalDateTime.now();
        this.user = user;
        this.team = team;
        this.team.getPartecipationRequestList().add(this);
        this.description = "In progress...";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Team getTeam() {
        return team;
    }

    public User getUser() {
        return user;
    }
}
