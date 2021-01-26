package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.CVUnit;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.Evaluation;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.model.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DesignerService {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private RepositoryHandler repositoryHandler;

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        PartecipationRequest<DesignerRole> pr =
                userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().createPartecipationRequest(project);
        repositoryHandler.getPartecipationRequestDesignerRepository().save(pr);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<Project> listProjectsByCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole()
                .getProjectsByCategory(repositoryHandler.getProjectRepository().findAll().iterator(), category);
    }

    public Set<Evaluation> listEvaluations(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getEvaluations();
    }

    public Set<CVUnit> listCV(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getCurriculumVitae();
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().getCategories();
    }

    public void addCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().addCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().removeCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }
}
