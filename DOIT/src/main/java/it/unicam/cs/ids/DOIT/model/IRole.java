package it.unicam.cs.ids.DOIT.model;

import java.util.Set;

public interface IRole {
    IUser getUser();
    Set<IProject> getProjects();
    void addProject(IProject project);
    Set<ICategory> getCategories();

}
