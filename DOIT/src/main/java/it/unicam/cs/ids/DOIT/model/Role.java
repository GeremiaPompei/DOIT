package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role {

    private User user;

    private List<Project> project;

    private Set<Category> categories;

    public Role(User user, Category category) {
        project = new ArrayList<>();
        categories = new HashSet<>();
        this.user = user;
        this.categories.add(category);
    }

    public User getUser() {
        return user;
    }

    public List<Project> getProjects() {
        return project;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "project=" + project.stream().map(p -> p.getId()).collect(Collectors.toSet()) +
                ", categories=" + categories.stream().map(p -> p.getName()).collect(Collectors.toSet()) +
                '}';
    }
}