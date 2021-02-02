package it.unicam.cs.ids.DOIT.project_manager;

import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.role.Evaluation;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectManagerService {
    @Autowired
    private RepositoryHandler repositoryHandler;

    public void upgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        Iterator<ProjectState> iterator = repositoryHandler.getProjectStateRepository().findAll().iterator();
        user.getRolesHandler(tokenUser).getProjectManagerRole().upgradeState(iterator, project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public void downgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        Iterator<ProjectState> iterator = repositoryHandler.getProjectStateRepository().findAll().iterator();
        user.getRolesHandler(tokenUser).getProjectManagerRole().downgradeState(iterator, project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        return user.getRolesHandler(tokenUser).getProjectManagerRole().getDesigners(project)
                .stream().map(l -> repositoryHandler.getUserRepository().findById(l).get()).collect(Collectors.toSet());
    }

    public void evaluate(Long idUser, Long tokenUser, Long idDesigner, Long projectId, Integer evaluation) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        if (repositoryHandler.getProjectStateRepository().findById(project.getProjectState().getId() + 1).orElse(null) != null)
            throw new IllegalArgumentException("You can't close a project if it is not in a terminal state!");
        Evaluation ev = user.getRolesHandler(tokenUser).getProjectManagerRole().insertEvaluation(userD, project, evaluation);
        repositoryHandler.getEvaluationRepository().save(ev);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public void exitAll(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        if (repositoryHandler.getProjectStateRepository().findById(project.getProjectState().getId() + 1).orElse(null) != null)
            throw new IllegalArgumentException("You can't close a project if it is not in a terminal state!");
        user.getRolesHandler(tokenUser).getProjectManagerRole().exitAll(project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getProjectRepository().save(project);
    }

    public ProjectState visualizeState(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        return user.getRolesHandler(tokenUser).getProjectManagerRole().getProjectState(project);
    }

    public void removeProject(Long iduser, Long tokenuser, Long idnextprojectmanager, Long idproject) {
        User thisUser = repositoryHandler.getUserRepository().findById(iduser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idproject).get();
        User nextprojectmanageruser = repositoryHandler.getUserRepository().findById(idnextprojectmanager).get();
        thisUser.getRolesHandler(tokenuser).getProjectManagerRole().removeProject(thisUser, nextprojectmanageruser, project);
        repositoryHandler.getProjectManagerRepository().save(nextprojectmanageruser.getRolesHandler().getProjectManagerRole());
        repositoryHandler.getUserRepository().save(thisUser);
        repositoryHandler.getUserRepository().save(nextprojectmanageruser);
        repositoryHandler.getProjectRepository().save(project);
    }
}
