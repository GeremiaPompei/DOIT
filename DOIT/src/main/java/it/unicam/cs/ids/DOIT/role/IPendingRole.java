package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;

import java.util.Set;

public interface IPendingRole extends IRole {
    Set<IPartecipationRequest> getPartecipationRequests();
}
