package it.unicam.cs.ids.DOIT;

public class ProjectProposer implements IRole{

    private User user;

    public Project createProject(int id, String name, String description) {
        return new Project(id,name,description);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
