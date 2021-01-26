package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequestRepository;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProgramManagerMVC {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PartecipationRequestRepository<ProgramManagerRole> partecipationRequestRepository;

    public Set<Project> listProjectsByCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole()
                .getProjectsByCategory(projectRepository.findAll().iterator(), category);
    }

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        PartecipationRequest<ProgramManagerRole> pr =
                userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().createPartecipationRequest(project);
        partecipationRequestRepository.save(pr);
        userRepository.save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getMyPartecipationRequests();
    }

    public void openRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().openRegistrations(project);
        projectRepository.save(project);
    }

    public void closeRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().closeRegistrations(project);
        projectRepository.save(project);
    }

    public Set<PartecipationRequest<DesignerRole>> listDesignerPR(Long idUser, Long tokenUser, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptDesignerPR(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        User userD = userRepository.findById(idDesigner).get();
        DesignerRole designerRole = userD.getRolesHandler().getDesignerRole();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().acceptPR(designerRole, project);
        userRepository.save(user);
        userRepository.save(userD);
    }

    public void removeDesignerPR(Long idUser, Long tokenUser, Long idDesigner, Long idProject, String reason) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        User userD = userRepository.findById(idDesigner).get();
        DesignerRole designerRole = userD.getRolesHandler().getDesignerRole();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removePR(designerRole, project, reason);
        userRepository.save(user);
        userRepository.save(userD);
    }

    public void removeDesigner(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        User userD = userRepository.findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removeDesigner(userD, project);
        userRepository.save(user);
        userRepository.save(userD);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        return this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getIdDesigners(project)
                .stream().map(l -> userRepository.findById(l).get()).collect(Collectors.toSet());
    }

    public void setProjectManager(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(idProject).get();
        User userD = userRepository.findById(idDesigner).get();
        this.userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().setProjectManager(userD, project);
        userRepository.save(user);
        userRepository.save(userD);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().addCategory(category);
        userRepository.save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().removeCategory(category);
        userRepository.save(user);
    }
}
