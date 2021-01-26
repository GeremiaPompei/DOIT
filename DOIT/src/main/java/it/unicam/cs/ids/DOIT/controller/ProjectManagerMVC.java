package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.project.ProjectStateRepository;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectManagerMVC {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;

    public void upgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        Iterator<ProjectState> iterator = projectStateRepository.findAll().iterator();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().upgradeState(iterator, project);
        projectRepository.save(project);
    }

    public void downgradeState(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        Iterator<ProjectState> iterator = projectStateRepository.findAll().iterator();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().downgradeState(iterator, project);
        projectRepository.save(project);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getDesigners(project)
                .stream().map(l -> userRepository.findById(l).get()).collect(Collectors.toSet());
    }

    public void evaluate(Long idUser, Long tokenUser, Long idDesigner, Long projectId, Integer evaluation) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        User userD = userRepository.findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().insertEvaluation(userD, project, evaluation);
        userRepository.save(user);
        userRepository.save(userD);
    }

    public void exitAll(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().exitAll(project);
        userRepository.save(user);
        projectRepository.save(project);
    }

    public ProjectState visualizeState(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getProjectState(project);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getCategories();
    }
}
