package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.user.IUser;
import it.unicam.cs.ids.DOIT.user.User;

public class FactoryModel implements IFactoryModel {

    private IResourceHandler resourceHandler;
    private IdGenerator idGenerator = new IdGenerator();

    FactoryModel(IResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public IProject createProject(String name, String description, IRole projectProposer, ICategory category) {
        IProject project = new Project(idGenerator.getId(), name, description, category);
        load(project);
        return project;
    }

    public ICategory createCategory(String name, String description) {
        ICategory category = new Category(name, description);
        load(category);
        return category;
    }

    public ProjectState createProjectState(int id, String name, String description) {
        ProjectState projectState = new ProjectState(id, name, description);
        load(projectState);
        return projectState;
    }

    public IUser createUser(String name, String surname, String birthdDay, String sex) {
        IUser user = new User(idGenerator.getId(), name, surname, birthdDay, sex);
        load(user);
        return user;
    }

    @Override
    public IPartecipationRequest createPartecipationRequest(IPendingRole role, ITeam team) {
        IPartecipationRequest partecipationRequest = new PartecipationRequest(role, team);
        load(partecipationRequest);
        return partecipationRequest;
    }

    @Override
    public ITeam createTeam(IProject project, ProjectProposerRole projectProposer) {
        return new Team(project, projectProposer);
    }

    private <T>void load(T t) {
        resourceHandler.insert(t);
    }

    @Override
    public <T extends IRole> T createRole(Class<T> clazz, int idUser, String idCategory) throws ReflectiveOperationException {
        return clazz.getConstructor(new Class<?>[]{Integer.class, String.class}).newInstance(new Object[]{idUser, idCategory});
    }
}
