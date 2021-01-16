package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(Integer idUser, String idCategory) {
        super(idUser, idCategory);
    }

    public void createProject(String name, String description, String idCategory)
            throws IllegalArgumentException {
        ICategory category = ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory);
        if (!this.getCategories().contains(category))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + category.getName() + "]");
        ServicesHandler.getInstance().getFactoryModel().createProject(name, description, category);
    }

    public Set<IUser> findProgramManagerList(String idCategory) {
        ICategory category = ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory);
        if (!this.getCategories().contains(category))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + category.getName() + "]");
        return ServicesHandler.getInstance().getResourceHandler().getUsersByCategoryAndRole(idCategory, ProgramManagerRole.class);
    }

    public void createTeam(int idUser, int idProject) throws RoleException {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(idUser);
        if (!user.getRole(ProgramManagerRole.class).getCategories().contains(
                ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getCategory()))
            throw new IllegalArgumentException("Il Proponente Progetto non possiede la categoria del progetto!");
        ITeam team = ServicesHandler.getInstance().getFactoryModel()
                .createTeam(ServicesHandler.getInstance().getResourceHandler().getProject(idProject), this);
        team.setProgramManager(user.getRole(ProgramManagerRole.class));
        super.enterTeam(team.getId());
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }

}