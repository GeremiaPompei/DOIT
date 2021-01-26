package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.model.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectProposerService {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private RepositoryHandler repositoryHandler;

    public void createProject(Long idUser, Long tokenUser, String name, String description, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        ProjectState projectState = repositoryHandler.getProjectStateRepository().findById(0L).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole()
                .createProject(name, description, category, projectState);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptPR(Long idUser, Long tokenUser, Long idProgramManager, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userPM = repositoryHandler.getUserRepository().findById(idProgramManager).get();
        ProgramManagerRole programManagerRole = userPM.getRolesHandler().getProgramManagerRole();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().acceptPR(programManagerRole, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userPM);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().addCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().removeCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

}
