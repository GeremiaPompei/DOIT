package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.history.IHistory;
import it.unicam.cs.ids.DOIT.project.IProject;

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
