package it.unicam.cs.ids.DOIT;

import java.util.List;

public abstract class User implements IUser {
    private int id;
    private String name;
    private String surname;
    private List<String> generalities;
    private List<IProject> projects;

    public User(int id, String name, String surname, List<String> generalities, List<IProject> projects) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.generalities = generalities;
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getGeneralities() {
        return generalities;
    }

    public List<IProject> getProjects() {
        return projects;
    }
}
