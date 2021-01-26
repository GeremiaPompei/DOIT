package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.role.PendingRole;
import org.springframework.data.repository.CrudRepository;

public interface PartecipationRequestRepository<T extends PendingRole> extends CrudRepository<PartecipationRequest<T>, Long> {
}
