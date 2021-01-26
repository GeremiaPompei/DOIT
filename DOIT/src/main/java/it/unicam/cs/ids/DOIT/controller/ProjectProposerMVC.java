package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
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

import java.util.Set;

@Service
public class ProjectProposerMVC {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public void createProject(Long idUser, Long tokenUser, String name, String description, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        ProjectState projectState = projectStateRepository.findById(0L).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole()
                .createProject(name, description, category, projectState);
        userRepository.save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptPR(Long idUser, Long tokenUser, Long idProgramManager, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        User userPM = userRepository.findById(idProgramManager).get();
        ProgramManagerRole programManagerRole = userPM.getRolesHandler().getProgramManagerRole();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().acceptPR(programManagerRole, project);
        userRepository.save(user);
        userRepository.save(userPM);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().addCategory(category);
        userRepository.save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole().removeCategory(category);
        userRepository.save(user);
    }

}
