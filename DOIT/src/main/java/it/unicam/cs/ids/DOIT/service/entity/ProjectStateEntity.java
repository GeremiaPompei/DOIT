package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ProjectStateEntity implements ResourceEntity<ProjectState> {
    @Transient
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();
    @Id
    private Long id;
    private String name;
    private String description;

    @Override
    public void fromObject(ProjectState projectState) {
        this.id = projectState.getId();
        this.name = projectState.getName();
        this.description = projectState.getDescription();
    }

    @Override
    public ProjectState toObject() {
        return servicesHandler.getFactoryModel().createProjectState(this.id, this.name, this.description);
    }
}
