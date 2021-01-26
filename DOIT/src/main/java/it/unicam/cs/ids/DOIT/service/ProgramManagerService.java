package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProgramManagerService {
    @Autowired
    private RepositoryHandler repositoryHandler;

    public Set<Project> listProjectsByCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole()
                .getProjectsByCategory(repositoryHandler.getProjectRepository().findAll().iterator(), category);
    }

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        PartecipationRequest<ProgramManagerRole> pr =
                user.getRolesHandler(tokenUser).getProgramManagerRole().createPartecipationRequest(project);
        repositoryHandler.getPartecipationRequestProgramManagerRepository().save(pr);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getMyPartecipationRequests();
    }

    public void openRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        user.getRolesHandler(tokenUser).getProgramManagerRole().openRegistrations(project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public void closeRegistrations(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        user.getRolesHandler(tokenUser).getProgramManagerRole().closeRegistrations(project);
        repositoryHandler.getProjectRepository().save(project);
    }

    public Set<PartecipationRequest<DesignerRole>> listDesignerPR(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptDesignerPR(Long idUser, Long tokenUser, Long idDesignerPR, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        PartecipationRequest<DesignerRole> pr = repositoryHandler.getPartecipationRequestDesignerRepository()
                .findById(idDesignerPR).get();
        DesignerRole designerRole = pr.getPendingRole();
        user.getRolesHandler(tokenUser).getProgramManagerRole().acceptPR(designerRole, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getPartecipationRequestDesignerRepository().save(pr);
    }

    public void removeDesignerPR(Long idUser, Long tokenUser, Long idDesignerPR, Long idProject, String reason) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        PartecipationRequest<DesignerRole> pr = repositoryHandler.getPartecipationRequestDesignerRepository()
                .findById(idDesignerPR).get();
        DesignerRole designerRole = pr.getPendingRole();
        user.getRolesHandler(tokenUser).getProgramManagerRole().removePR(designerRole, project, reason);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getPartecipationRequestDesignerRepository().save(pr);
    }

    public void removeDesigner(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        user.getRolesHandler(tokenUser).getProgramManagerRole().removeDesigner(userD, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public Set<User> listDesigners(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getIdDesigners(project)
                .stream().map(l -> repositoryHandler.getUserRepository().findById(l).get()).collect(Collectors.toSet());
    }

    public void setProjectManager(Long idUser, Long tokenUser, Long idDesigner, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        User userD = repositoryHandler.getUserRepository().findById(idDesigner).get();
        user.getRolesHandler(tokenUser).getProgramManagerRole().setProjectManager(userD, project);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getUserRepository().save(userD);
    }

    public Set<Project> listHistory(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getHistory();
    }

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getProgramManagerRole().getCategories();
    }
}
