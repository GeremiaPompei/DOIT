package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;

import java.util.Set;

public interface IPartecipationRequestHandler<T extends PendingRole> {
    void acceptPR(T role, Team team);

    void removePR(T role, Team team, String description);

    Set<PartecipationRequest<T>> getPartecipationRequestsByTeam(Team team);
}
