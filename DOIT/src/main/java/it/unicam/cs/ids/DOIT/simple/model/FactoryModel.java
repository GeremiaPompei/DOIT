package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;

public class FactoryModel implements IFactoryModel {

    IResourceHandler resourceHandler;

    public FactoryModel(IResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public IProject createProject(int id, String name, String description, IUser projectProposer, ICategory category) {
        IProjectState projectState = resourceHandler.searchOne(IProjectState.class, (t) -> t.getId() == 0);
        IProject project = new Project(id, name, description, projectProposer, category, projectState);
        load(project);
        return project;
    }

    public ICategory createCategory(String name, String description) {
        Category category = new Category(name, description);
        load(category);
        return category;
    }

    public IProjectState createProjectState(int id, String name, String description) {
        IProjectState projectState = new ProjectState(id, name, description);
        load(projectState);
        return projectState;
    }

    public IUser createUser(int id, String name, String surname, int birthdDay, String sex) {
        IUser user = new User(id, name, surname, birthdDay, sex);
        load(user);
        return user;
    }

    @Override
    public IPartecipationRequest createPartecipationRequest(IUser user, ITeam team) {
        IPartecipationRequest partecipationRequest = new PartecipationRequest(user, team);
        load(partecipationRequest);
        return partecipationRequest;
    }

    @Override
    public ITeam createTeam(IProject project, IUser user) {
        return new Team(project, user);
    }

    private <T>void load(T t) {
        resourceHandler.insert(t);
    }

    @Override
    public <T extends IRole> T createRole(Class<T> clazz, IUser user, ICategory category) throws ReflectiveOperationException {
        return clazz.getConstructor(new Class<?>[]{IUser.class, ICategory.class, IFactoryModel.class}).newInstance(new Object[]{user, category, this});
    }

    @Override
    public IHistory createHisory() {
        return new History(this);
    }

    public IHistoryUnit createHistoryUnit(boolean bool){
        return new HistoryUnit(bool);
    }
}