package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private int id;
    private String name;
    private String surname;
    private Category category;
    private List<String> generalities;
    private GestoreRuoli gestoreRuoli;

    public User(int id, String name, String surname, List<String> generalities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.generalities = generalities;
        this.gestoreRuoli = new GestoreRuoli(this);
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

    public GestoreRuoli getGestoreRuoli() {
        return gestoreRuoli;
    }

    public Category getCategory() {
        return category;
    }

    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        this.getGestoreRuoli().getRoles().stream()
                .map(list -> projects.addAll(list.getProjects()))
                .collect(Collectors.toList());
        return projects;
    }
}
