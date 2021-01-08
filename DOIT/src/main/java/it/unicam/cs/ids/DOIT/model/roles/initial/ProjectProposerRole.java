package it.unicam.cs.ids.DOIT.model.roles.initial;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public Project createProject(int id, String name, String description, Category category)
            throws IllegalArgumentException {
        if (!this.getCategories().contains(category))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + category.getName() + "]");
        Project project = new Project(id, name, description, this.getUser(), category);
        super.addProject(project);
        return project;
    }

    public Set<User> findProgramManagerList(Category category) {
        return GestoreRisorse.getInstance().search(User.class, u -> {
            try {
                return u.getRole(ProgramManagerRole.class).getCategories().contains(category);
            } catch (RoleException e) {
                return false;
            }
        });
    }

    public Team createTeam(User user, Project project) throws RoleException {
        if (!user.getRole(ProgramManagerRole.class).getCategories().contains(project.getCategory()))
            throw new IllegalArgumentException("Il Proponente Progetto non possiede la categoria del progetto:[" +
                    project.getCategory().getName() + "]!");
        return user.getRole(ProgramManagerRole.class).createTeam(project);
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }
}