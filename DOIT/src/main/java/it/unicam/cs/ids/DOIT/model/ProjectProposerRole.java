package it.unicam.cs.ids.DOIT.model;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public void createProject(int id, String name, String description, Category category) {
        super.getProjects().add(new Project(id, name, description, this.getUser(), category));
    }

    public void becomeProgramManager(Category category) throws ReflectiveOperationException {
        this.getUser().addRole(ProgramManagerRole.class, new Object[]{this.getUser(), category},
                User.class, Category.class);
    }


}