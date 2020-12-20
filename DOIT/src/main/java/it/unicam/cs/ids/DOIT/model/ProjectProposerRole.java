package it.unicam.cs.ids.DOIT.model;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public Project createProject(int id, String name, String description, Category category) {
        Project project = new Project(id, name, description, this.getUser(), category);
        super.getProjects().add(project);
        return project;
    }

    public void becomeProgramManager(Category category) throws ReflectiveOperationException {
        this.getUser().addRole(ProgramManagerRole.class, category);
    }


}