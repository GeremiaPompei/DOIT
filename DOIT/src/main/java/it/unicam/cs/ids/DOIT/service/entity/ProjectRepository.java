package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}

