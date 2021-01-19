package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;

import java.util.Set;

public interface IPartecipationRequestHandler {
    void acceptPR(int idRole, int idProject) throws RoleException;

    void removePR(int idRole, int idProject, String description);

    Set<IPartecipationRequest> getPartecipationRequestsByTeam(int idProject);
}
