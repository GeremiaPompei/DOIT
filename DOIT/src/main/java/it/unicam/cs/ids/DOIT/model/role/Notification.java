package it.unicam.cs.ids.DOIT.model.role;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @Column(name = "ID_Role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String notificate;
    private Timestamp localDateTime;

    public Notification() {
    }

    public Notification(String notificate) {
        this.notificate = notificate;
        localDateTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getNotificate() {
        return notificate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime.toLocalDateTime();
    }
}
