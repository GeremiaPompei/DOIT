package it.unicam.cs.ids.DOIT.model;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(User user) {
        super(user);
    }

    public void createProject(int id, String name, String description, Category category) {
        super.getProject().add(new Project(id, name, description, this.getUser(), category));
    }

    public void becomeProgramManager() throws ReflectiveOperationException {
        this.getUser().addRole(ProgramManagerRole.class, new Object[]{this.getUser()}, User.class);
    }


}