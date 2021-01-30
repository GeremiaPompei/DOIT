package it.unicam.cs.ids.DOIT.model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Team {
    private boolean open;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Team")
    private Long id;

    @JoinColumn(name = "ID_ProjectProposer")
    @OneToOne
    @JsonIgnoreProperties({"projects", "history"})
    private ProjectProposerRole projectProposer;

    @JoinColumn(name = "ID_ProjectManager")
    @OneToOne
    @JsonIgnoreProperties({"projects", "history"})
    private ProjectManagerRole projectManager;

    @JoinColumn(name = "ID_ProgramManager")
    @OneToOne
    @JsonIgnoreProperties({"projects", "history"})
    private ProgramManagerRole programManager;

    @OneToMany
    @JsonIgnoreProperties({"projects", "history"})
    private Set<DesignerRole> designers;
    @OneToMany
    @JsonIgnoreProperties({"project", "pendingRole"})
    private Set<PartecipationRequest<DesignerRole>> designerRequest;
    @OneToMany
    @JsonIgnoreProperties({"project", "pendingRole"})
    private Set<PartecipationRequest<ProgramManagerRole>> programManagerRequest;

    public Team(ProjectProposerRole projectProposer) {
        this.open = false;
        this.projectProposer = projectProposer;
        this.designers = new HashSet<>();
        this.designerRequest = new HashSet<>();
        this.programManagerRequest = new HashSet<>();
    }

    public Team() {
    }

    public boolean isOpen() {
        return open;
    }

    public ProgramManagerRole getProgramManager() {
        return programManager;
    }

    public void addDesigner(DesignerRole designer) {
        this.designers.add(designer);
    }

    public void removeDesigner(DesignerRole designer) {
        this.designers.remove(designer);
    }

    public Set<DesignerRole> getDesigners() {
        return designers;
    }

    public Set<PartecipationRequest<DesignerRole>> getDesignerRequest() {
        return designerRequest;
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getProgramManagerRequest() {
        return this.programManagerRequest;
    }

    public void setProgramManager(ProgramManagerRole programManagerRole) {
        this.programManager = programManagerRole;
    }

    public void setProjectManager(ProjectManagerRole projectManager) {
        this.projectManager = projectManager;
    }

    public ProjectProposerRole getProjectProposer() {
        return projectProposer;
    }

    public ProjectManagerRole getProjectManager() {
        return projectManager;
    }

    public void openRegistrations() {
        this.open = true;
    }

    public void closeRegistrations() {
        this.open = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}