package it.unicam.cs.ids.DOIT.entity.role;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Notification {

    @Id
    @Column(name = "id_notification")
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

    public String getLocalDateTime() {
        return localDateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
