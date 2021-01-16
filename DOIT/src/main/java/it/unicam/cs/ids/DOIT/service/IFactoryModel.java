package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.role.ITeam;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.user.IUser;

public interface IFactoryModel {
    IProject createProject(String name, String description, IRole projectProposer, ICategory category);

    ICategory createCategory(String name, String description);

    ProjectState createProjectState(int id, String name, String description);

    IUser createUser(String name, String surname, String birthdDay, String sex);

    IPartecipationRequest createPartecipationRequest(IPendingRole role, ITeam team);

    ITeam createTeam(IProject project, ProjectProposerRole projectProposer);

    <T extends IRole> T createRole(Class<T> clazz, int idUser, String idCategory) throws ReflectiveOperationException;
}
