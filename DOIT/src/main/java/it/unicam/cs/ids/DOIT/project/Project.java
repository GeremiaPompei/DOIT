package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.ITeam;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Project implements IProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private ProjectState projectState;
    private ITeam team;
    private ICategory category;

    public Project(String name, String description, ICategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.projectState = ServicesHandler.getInstance().getResourceHandler().getProjectState(0L);
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

    public ITeam getTeam() {
        return team;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ICategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectState=" + projectState +
                ", category=" + category +
                ", team=" + team +
                '}';
    }
}