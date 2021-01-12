package it.unicam.cs.ids.DOIT.model.roles.initial;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.IProjectProposerRole;

import java.util.function.Predicate;

public class ProjectProposerRole extends Role implements IProjectProposerRole {

    public ProjectProposerRole(IUser user, ICategory category) {
        super(user, category);
    }

    public IProject createProject(int id, String name, String description, ICategory category, IFactoryModel factory)
            throws IllegalArgumentException {
        if (!this.getCategories().contains(category))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + category.getName() + "]");
        IProject project = factory.createProject(id, name, description, this.getUser(), category);
        super.addProject(project);
        return project;
    }

    public Predicate<IUser> findProgramManagerList(ICategory category) {
        return u -> {
            try {
                return u.getRole(ProgramManagerRole.class).getCategories().contains(category);
            } catch (RoleException e) {
                return false;
            }
        };
    }

    public ITeam createTeam(IUser user, IProject project, IFactoryModel factory) throws RoleException {
        if (!user.getRole(ProgramManagerRole.class).getCategories().contains(project.getCategory()))
            throw new IllegalArgumentException("Il Proponente Progetto non possiede la categoria del progetto:[" +
                    project.getCategory().getName() + "]!");
        return user.getRole(ProgramManagerRole.class).createTeam(project, factory);
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }
}