package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.Category;
import it.unicam.cs.ids.DOIT.model.Project;
import it.unicam.cs.ids.DOIT.model.Role;
import it.unicam.cs.ids.DOIT.model.User;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public Project createProject(int id, String name, String description, Category category)
            throws IllegalArgumentException {
        if (!this.getCategories().contains(category))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + category.getName() + "]");
        Project project = new Project(id, name, description, this.getUser(), category);
        super.getProjects().add(project);
        return project;
    }

}