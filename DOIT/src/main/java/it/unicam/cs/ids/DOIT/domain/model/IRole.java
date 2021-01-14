package it.unicam.cs.ids.DOIT.domain.model;

import java.util.Set;

public interface IRole {
    IUser getUser();

    Set<IProject> getProjects();

    void enterProject(IProject project);

    void exitProject(IProject project);

    Set<ICategory> getCategories();

    void addCategory(ICategory category);

    void removeCategory(ICategory category);

    IHistory getCronology();
}
