package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.model.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProgramManagerService {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private RepositoryHandler repositoryHandler;

    public Set<Project> listProjectsByCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole()
                .getProjectsByCategory(repositoryHandler.getProjectRepository().findAll().iterator(), category);
    }

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        PartecipationRequest<ProgramManagerRole> pr =
                userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().createPartecipationRequest(project);
        repositoryHandler.getPartecipationRequestProgramManagerRepository().save(pr);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getMyPartecipationRequests();
    }

    public void openRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().openRegistrations(project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public void closeRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().closeRegistrations(project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public Set<PartecipationRequest<DesignerRole>> listDesignerPR(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptDesignerPR(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        DesignerRole designerRole = userD.getRolesHandler().getDesignerRole();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().acceptPR(designerRole, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public void removeDesignerPR(Long idUser, Long tokenUser, Long idDesigner, Long idProject, String reason) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        DesignerRole designerRole = userD.getRolesHandler().getDesignerRole();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removePR(designerRole, project, reason);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public void removeDesigner(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removeDesigner(userD, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getIdDesigners(project)
                .stream().map(l -> repositoryHandler.getUserRepository().findById(l).get()).collect(Collectors.toSet());
    }

    public void setProjectManager(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().setProjectManager(userD, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().addCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removeCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }
}
