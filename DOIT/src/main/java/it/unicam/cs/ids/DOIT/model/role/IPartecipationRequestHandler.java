package it.unicam.cs.ids.DOIT.model.role;

import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;

import java.util.Set;

public interface IPartecipationRequestHandler<T extends IPendingRole> {
    void acceptPR(T role, Project projectInput);

    void removePR(T role, Project projectInput, String description);

    Set<PartecipationRequest<T>> getPartecipationRequestsByProject(Project projectInput);
}
