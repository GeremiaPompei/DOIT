package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;

import java.util.Set;

public interface IPartecipationRequestHandler<T extends IPendingRole> {
    void acceptPR(PartecipationRequest<T> pr);

    void removePR(PartecipationRequest<T> pr, String description);

    Set<PartecipationRequest<T>> getPartecipationRequestsByProject(Project projectInput);
}
