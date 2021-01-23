package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.role.IPendingRole;
import it.unicam.cs.ids.DOIT.role.ITeam;

import java.time.LocalDateTime;

public interface IPartecipationRequest {
    IPendingRole getPendingRole();

    void displayed(String description);

    ITeam getTeam();

    String getDescription();

    boolean getState();

    LocalDateTime getDateTime();

    void setState(boolean state);

    void setDescription(String description);

    void setDateTime(LocalDateTime dateTime);
}
