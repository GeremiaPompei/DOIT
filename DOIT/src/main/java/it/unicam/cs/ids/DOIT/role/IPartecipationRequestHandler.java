package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;

import java.util.Set;

public interface IPartecipationRequestHandler<T extends PendingRole> {
    void acceptPR(T role, Project projectInput);

    void removePR(T role, Project projectInput, String description);

    Set<PartecipationRequest<T>> getPartecipationRequestsByProject(Project projectInput);
}
