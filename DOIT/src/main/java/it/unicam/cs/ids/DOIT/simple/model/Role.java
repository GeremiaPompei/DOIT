package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.*;

import java.util.HashSet;
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

    public IHistory getCronology(){
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
    public void exitProject(IProject project){
        cronology.exitProject(project);
        projects.remove(project);
    }

     @Override
    public void enterProject(IProject project) {
        cronology.enterProject(project);
        projects.add(project);
    }
}