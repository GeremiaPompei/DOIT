package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequestRepository;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DesignerMVC {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PartecipationRequestRepository<DesignerRole> partecipationRequestRepository;

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        PartecipationRequest<DesignerRole> pr =
                userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().createPartecipationRequest(project);
        partecipationRequestRepository.save(pr);
        userRepository.save(user);
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().addCategory(category);
        userRepository.save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().removeCategory(category);
        userRepository.save(user);
    }
}
