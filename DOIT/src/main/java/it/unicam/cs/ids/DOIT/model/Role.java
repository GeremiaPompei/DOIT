package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role implements IRole{

    private IUser user;

    private Set<IProject> project;

    private Set<ICategory> categories;

    public Role(IUser user, ICategory category) {
        project = new HashSet<>();
        categories = new HashSet<>();
        this.user = user;
        this.categories.add(category);
    }

    public IUser getUser() {
        return user;
    }

    public Set<IProject> getProjects() {
        return project;
    }

    public void addProject(IProject project) {
        this.project.add(project);
        //TODO Aggiornamento history
    }

    public Set<ICategory> getCategories() {
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