package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.ITeam;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Project implements IProject {

    @Id
    @Column(name = "id_project")
    private Long id = ServicesHandler.getInstance().getIdGenerator().getId();
    private String name;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projectstate")
    private ProjectState projectState;
    //private ITeam team;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
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
        //this.team = team;
    }

    public ITeam getTeam() {
        return null;//team;
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
                ", team=" + "team" +
                '}';
    }
}