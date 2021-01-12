package it.unicam.cs.ids.DOIT.model;

public interface IProject {
    void setProjectManager(IUser projectManager);
    void setTeam(ITeam team);
    ITeam getTeam();
    IUser getProjectManager();
    int getId();
    String getName();
    String getDescription();
    IUser getProjectProposer();
    ICategory getCategory();
    IProjectState getProjectState();
    void setProjectState(IProjectState projectState);
}
