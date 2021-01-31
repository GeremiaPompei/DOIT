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
    private String notification;
    private Timestamp localDateTime;

    public Notification() {
    }

    public Notification(String notification) {
        this.notification = notification;
        localDateTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getNotification() {
        return notification;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime.toLocalDateTime();
    }
}
