package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

