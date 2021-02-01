package it.unicam.cs.ids.DOIT.entity.role;

import java.util.List;

public interface ISubscriber {
    void notify(String notification);

    void removeNotifications();

    List<Notification> getNotications();
}
