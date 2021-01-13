package it.unicam.cs.ids.DOIT.domain.model;

import java.util.Set;

public interface IRole {
    IUser getUser();
    Set<IProject> getProjects();
    void enterProject(IProject project);
    Set<ICategory> getCategories();
    void exitProject(IProject project);
}
