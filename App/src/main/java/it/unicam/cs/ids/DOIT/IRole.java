package it.unicam.cs.ids.DOIT;

import java.util.List;

public interface IRole {
    void setUser(User user);
    List<Project> getProjects();
}
