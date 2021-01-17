package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;

public class ProjectProposerRole extends Role {

    public ProjectProposerRole(Integer idUser, String idCategory) {
        super(idUser, idCategory);
    }

    public void createProject(String name, String description, String idCategory)
            throws IllegalArgumentException {
        ServicesHandler.getInstance().getFactoryModel().createProject(name, description, getInnerCategory(idCategory));
    }

    public Set<IUser> findProgramManagerList(String idCategory) {
        return ServicesHandler.getInstance().getResourceHandler()
                .getUsersByCategoryAndRole(getInnerCategory(idCategory).getName(), ProgramManagerRole.class);
    }

    public void createTeam(int idUser, int idProject) throws RoleException {
        ProgramManagerRole pm = ServicesHandler.getInstance().getResourceHandler().getUser(idUser).getRole(ProgramManagerRole.class);
        IProject project = ServicesHandler.getInstance().getResourceHandler().getProject(idProject);
        if (!pm.getCategories().contains(getInnerCategory(project.getCategory().getName())))
            throw new IllegalArgumentException("Il Proponente Progetto non possiede la categoria del progetto!");
        ITeam team = ServicesHandler.getInstance().getFactoryModel().createTeam(project, this);
        team.setProgramManager(pm);
        super.enterTeam(team.getId());
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }

}