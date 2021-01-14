package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role implements IRole {

    private IUser user;

    private Set<IProject> projects;

    private Set<ICategory> categories;

    private IHistory cronology;

    public Role(IUser user, ICategory category, IFactoryModel factoryModel) {
        projects = new HashSet<>();
        categories = new HashSet<>();
        this.user = user;
        this.categories.add(category);
        cronology = factoryModel.createHisory();
    }

    public IHistory getCronology() {
        return cronology;
    }

    public IUser getUser() {
        return user;
    }

    public Set<IProject> getProjects() {
        return projects;
    }

    public Set<ICategory> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Role{" +
                "project=" + projects.stream().map(p -> p.getId()).collect(Collectors.toSet()) +
                ", categories=" + categories.stream().map(p -> p.getName()).collect(Collectors.toSet()) +
                '}';
    }

    @Override
    public void exitProject(IProject project) {
        cronology.exitProject(project);
        projects.remove(project);
    }

    @Override
    public void enterProject(IProject project) {
        cronology.enterProject(project);
        projects.add(project);
    }

    @Override
    public void addCategory(ICategory category) {
        this.categories.add(category);
    }

    @Override
    public void removeCategory(ICategory category) {
        if (this.categories.size() == 1)
            throw new IllegalArgumentException("Non si puo eliminare una categoria quando ne rimane solo una!");
        if (this.projects.stream().filter(p -> p.getCategory().equals(category)).findAny().orElse(null) != null)
            throw new IllegalArgumentException("Non puo essere eliminata una categoria in uso su uno dei progetti appartenenti al ruolo!");
        this.categories.remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(this.getClass(), o.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }
}