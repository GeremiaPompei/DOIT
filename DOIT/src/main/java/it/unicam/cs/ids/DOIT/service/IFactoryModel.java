package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.history.IHistory;
import it.unicam.cs.ids.DOIT.history.IHistoryUnit;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.IProjectState;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;

public interface IFactoryModel {
    IProject createProject(int id, String name, String description, IUser projectProposer, ICategory category);
    ICategory createCategory(String name, String description);
    IProjectState createProjectState(int id, String name, String description);
    IUser createUser(int id, String name, String surname, int birthdDay, String sex);
    IPartecipationRequest createPartecipationRequest(IUser user, ITeam team);
    ITeam createTeam(IProject project, IUser user);
    <T extends IRole> T createRole(Class<T> clazz, IUser user, ICategory category) throws ReflectiveOperationException;
    IHistory createHisory();
    IHistoryUnit createHistoryUnit(boolean bool);
}
