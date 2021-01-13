package it.unicam.cs.ids.DOIT.domain.model;

public interface IPartecipationRequest {
    IUser getUser();

    void displayed(String description);

    ITeam getTeam();

    String getDescription();

    boolean getState();
}
