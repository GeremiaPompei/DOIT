package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.project.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
