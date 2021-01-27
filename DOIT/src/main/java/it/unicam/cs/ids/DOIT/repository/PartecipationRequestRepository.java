package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.role.IPendingRole;
import org.springframework.data.repository.CrudRepository;

public interface PartecipationRequestRepository<T extends IPendingRole> extends CrudRepository<PartecipationRequest<T>, Long> {
}
