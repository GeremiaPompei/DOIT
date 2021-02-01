package it.unicam.cs.ids.DOIT.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.role.DesignerRole;
import it.unicam.cs.ids.DOIT.entity.role.IPendingRole;
import it.unicam.cs.ids.DOIT.entity.role.ProgramManagerRole;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PartecipationRequest<T extends IPendingRole> {

    @Id
    @Column(name = "ID_PartecipationRequest")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean state;
    private String description;
    private Timestamp dateTime;

    @Any(metaColumn = @Column(name = "ROLE"))
    @AnyMetaDef(
            idType = "long",
            metaType = "string",
            metaValues = {
                    @MetaValue(value = "DesignerRole", targetEntity = DesignerRole.class),
                    @MetaValue(value = "ProgramManagerRole", targetEntity = ProgramManagerRole.class)
            })
    @JoinColumn(name = "ID_PendingRole")
    private T pendingRole;

    @OneToOne()
    @JoinColumn(name = "ID_Team")
    @JsonIgnoreProperties("team")
    private Project project;

    public PartecipationRequest() {
    }

    public PartecipationRequest(T role, Project project) {
        this.pendingRole = role;
        this.project = project;
        this.description = "Partecipation request sent...";
        this.state = false;
        this.dateTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public T getPendingRole() {
        return pendingRole;
    }

    public void displayed(String description) {
        this.state = true;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getDescription() {
        return description;
    }

    public boolean getState() {
        return state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime.toLocalDateTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartecipationRequest that = (PartecipationRequest) o;
        return Objects.equals(pendingRole, that.pendingRole) && Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pendingRole, project);
    }
}