package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.model.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectManagerService {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private RepositoryHandler repositoryHandler;

    public void upgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        Iterator<ProjectState> iterator = repositoryHandler.getProjectStateRepository().findAll().iterator();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().upgradeState(iterator, project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public void downgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        Iterator<ProjectState> iterator = repositoryHandler.getProjectStateRepository().findAll().iterator();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().downgradeState(iterator, project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getDesigners(project)
                .stream().map(l -> repositoryHandler.getUserRepository().findById(l).get()).collect(Collectors.toSet());
    }

    public void evaluate(Long idUser, Long tokenUser, Long idDesigner, Long projectId, Integer evaluation) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().insertEvaluation(userD, project, evaluation);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public void exitAll(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().exitAll(project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getProjectRepository().save(project);
    }

    public ProjectState visualizeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getProjectState(project);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getCategories();
    }
}
