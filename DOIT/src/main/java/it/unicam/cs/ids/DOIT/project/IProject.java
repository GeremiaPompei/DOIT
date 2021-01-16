package it.unicam.cs.ids.DOIT.project;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.ITeam;

public interface IProject {

    int getId();

    String getName();

    String getDescription();

    void setTeam(ITeam team);

    ITeam getTeam();

    ProjectState getProjectState();

    void setProjectState(ProjectState projectState);

    ICategory getCategory();
}
