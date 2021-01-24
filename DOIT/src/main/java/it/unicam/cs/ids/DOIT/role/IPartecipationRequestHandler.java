package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;

import java.util.Set;

public interface IPartecipationRequestHandler {
    void acceptPR(Long idRole, Long idProject) throws RoleException;

    void removePR(Long idRole, Long idProject, String description);

    Set<PartecipationRequest> getPartecipationRequestsByTeam(Long idProject);
}
