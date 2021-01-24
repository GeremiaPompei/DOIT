package it.unicam.cs.ids.DOIT.role;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class CVUnit {

    @Id
    @Column(name = "ID_CVUnit")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String experience;

    public CVUnit(LocalDate localDate, String experience) {
        this.date = Date.valueOf(localDate);
        this.experience = experience;
    }

    public LocalDate getTimestamp() {
        return date.toLocalDate();
    }

    public String getExperience() {
        return experience;
    }
}
