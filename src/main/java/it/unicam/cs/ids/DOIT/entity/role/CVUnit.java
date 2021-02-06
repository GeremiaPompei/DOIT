package it.unicam.cs.ids.DOIT.entity.role;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class CVUnit {

    @Id
    @Column(name = "ID_CVUnit")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateStart;
    private Date dateStop;
    private String experience;

    public CVUnit(LocalDate localDate, String experience) {
        this.dateStart = Date.valueOf(localDate);
        this.experience = experience;
    }

    public CVUnit(LocalDate dateStart, LocalDate dateStop, String experience) {
        this.dateStart = Date.valueOf(dateStart);
        this.dateStop = Date.valueOf(dateStop);
        this.experience = experience;
    }

    public CVUnit() {
    }

    public LocalDate getDateStart() {
        return dateStart.toLocalDate();
    }

    public LocalDate getDateStop() {
        return dateStop.toLocalDate();
    }

    public String getExperience() {
        return experience;
    }
}
