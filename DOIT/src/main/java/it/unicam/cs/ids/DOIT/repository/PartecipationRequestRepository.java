package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.role.IPendingRole;
import org.springframework.data.repository.CrudRepository;

public interface PartecipationRequestRepository<T extends IPendingRole> extends CrudRepository<PartecipationRequest<T>, Long> {
}
