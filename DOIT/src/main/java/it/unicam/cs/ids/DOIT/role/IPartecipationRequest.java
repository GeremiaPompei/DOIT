package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.project.ITeam;

public interface IPartecipationRequest {
    IUser getUser();

    void displayed(String description);

    ITeam getTeam();

    String getDescription();

    boolean getState();
}
