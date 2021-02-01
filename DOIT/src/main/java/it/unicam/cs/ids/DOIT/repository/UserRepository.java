package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

