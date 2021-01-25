package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.role.PendingRole;
import org.springframework.data.repository.CrudRepository;

public interface PartecipationRequestRepository<T extends PendingRole> extends CrudRepository<PartecipationRequest<T>, Long> {
}
