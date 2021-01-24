package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.role.Team;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.user.User;

public interface IFactoryModel {
    Project createProject(String name, String description, Category category);

    Category createCategory(String name, String description);

    ProjectState createProjectState(Long id, String name, String description);

    User createUser(String name, String surname, String birthdDay, String sex, String email, String password);

    PartecipationRequest createPartecipationRequest(PendingRole role, Team team);

    Team createTeam(Project project, ProjectProposerRole projectProposer);

    <T extends Role> T createRole(Class<T> clazz, User user, Category category) throws ReflectiveOperationException;
}
