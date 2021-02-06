package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.entity.role.ProjectManagerRole;
import org.springframework.data.repository.CrudRepository;

public interface ProjectManagerRepository extends CrudRepository<ProjectManagerRole, Long> {
}
