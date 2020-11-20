package it.unicam.cs.ids.DOIT;

import java.util.List;

public interface IUser {
    int getId();
    String getName();
    String getSurname();
    List<String> getGeneralities();
    List<IProject> getProjects();
}
