package it.unicam.cs.ids.DOIT.service.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartecipationRequestRepository extends CrudRepository<PartecipationRequestEntity, String> {
}
