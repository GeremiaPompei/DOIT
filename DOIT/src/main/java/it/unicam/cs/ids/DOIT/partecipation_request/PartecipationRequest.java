package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.PendingRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.role.Team;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PartecipationRequest<T extends PendingRole> {

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
            metaType = "long",
            metaValues = {
                    @MetaValue(value = "1", targetEntity = DesignerRole.class),
                    @MetaValue(value = "1", targetEntity = ProgramManagerRole.class)
            })
    @JoinColumn(name = "ID_Role")
    private T role;

    @OneToOne()
    @JoinColumn(name = "ID_Team")
    private Team team;

    public PartecipationRequest() {
    }

    public PartecipationRequest(T role, Team team) {
        this.role = role;
        this.team = team;
        this.description = "Partecipation request sent...";
        this.state = false;
        this.dateTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public T getPendingRole() {
        return role;
    }

    public void displayed(String description) {
        this.state = true;
        this.description = description;
    }

    public Team getTeam() {
        return team;
    }

    public String getDescription() {
        return description;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = Timestamp.valueOf(dateTime);
    }

    public LocalDateTime getDateTime() {
        return this.dateTime.toLocalDateTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartecipationRequest that = (PartecipationRequest) o;
        return Objects.equals(role, that.role) && Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, team);
    }
}