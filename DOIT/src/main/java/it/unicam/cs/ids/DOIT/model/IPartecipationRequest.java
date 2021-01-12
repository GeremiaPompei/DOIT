package it.unicam.cs.ids.DOIT.model;

public interface IPartecipationRequest {
    IUser getDesigner();

    void displayed(String description);

    ITeam getTeam();

    String getDescription();

    boolean getState();
}
