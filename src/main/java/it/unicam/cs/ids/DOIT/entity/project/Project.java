package it.unicam.cs.ids.DOIT.entity.project;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.role.Team;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @Column(name = "id_project")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_project_state")
    private ProjectState projectState;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_team")
    private Team team;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private Category category;

    public Project(String name, String description, Category category, ProjectState projectState) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.projectState = projectState;
    }

    public Project() {
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
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

    public Category getCategory() {
        return category;
    }

    public void clearInitProject() {
        this.category = null;
        this.projectState = null;
        this.getTeam().clearInitTeam();
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
}