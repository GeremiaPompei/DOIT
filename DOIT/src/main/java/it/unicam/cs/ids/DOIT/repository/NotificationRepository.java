package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.role.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
