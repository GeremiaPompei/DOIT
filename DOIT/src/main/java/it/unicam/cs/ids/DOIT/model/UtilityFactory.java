package it.unicam.cs.ids.DOIT.model;

public class UtilityFactory {

    private IResourceHandler resourceHandler;

    public UtilityFactory(IResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public Project createProject(int id, String name, String description, User projectProposer, Category category) {
        ProjectState projectState = resourceHandler.searchOne(ProjectState.class, (t) -> t.getId() == 0);
        Project project = new Project(id, name, description, projectProposer, category, projectState);
        resourceHandler.getRisorse().get(Project.class).add(project);
        return project;
    }

    public Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        resourceHandler.getRisorse().get(Category.class).add(category);
        return category;
    }

    public ProjectState createProjectState(int id, String name, String description) {
        ProjectState projectState = new ProjectState(id, name, description);
        resourceHandler.getRisorse().get(ProjectState.class).add(projectState);
        return projectState;
    }

    public User createUser(int id, String name, String surname, int birthdDay, String gender) {
        User user = new User(id, name, surname, birthdDay, gender);
        resourceHandler.getRisorse().get(User.class).add(user);
        return user;
    }
}
