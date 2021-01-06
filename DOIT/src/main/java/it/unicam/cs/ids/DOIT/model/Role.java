package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role {

    private User user;

    private Set<Project> project;

    private Set<Category> categories;

    public Role(User user, Category category) {
        project = new HashSet<>();
        categories = new HashSet<>();
        this.user = user;
        this.categories.add(category);
    }

    public User getUser() {
        return user;
    }

    public Set<Project> getProjects() {
        return project;
    }

    public void addProject(Project project) {
        this.project.add(project);
        //TODO Aggiornamento history
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Role{" +
                "project=" + project.stream().map(p -> p.getId()).collect(Collectors.toSet()) +
                ", categories=" + categories.stream().map(p -> p.getName()).collect(Collectors.toSet()) +
                '}';
    }
}