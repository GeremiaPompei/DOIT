package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectProposerService {

    @Autowired
    private RepositoryHandler repositoryHandler;

    public void createProject(Long idUser, Long tokenUser, String name, String description, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        ProjectState projectState = repositoryHandler.getProjectStateRepository().findById(0L).get();
        user.getRolesHandler(tokenUser).getProjectProposerRole().createProject(name, description, category, projectState);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> listPR(Long idUser, Long tokenUser, Long idProject) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idProject).get();
        return user.getRolesHandler(tokenUser).getProjectProposerRole().getPartecipationRequestsByProject(project);
    }

    public void acceptPR(Long idUser, Long tokenUser, Long idProgramManagerPR) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        PartecipationRequest<ProgramManagerRole> pr =
                repositoryHandler.getPartecipationRequestProgramManagerRepository().findById(idProgramManagerPR).get();
        user.getRolesHandler(tokenUser).getProjectProposerRole().acceptPR(pr);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getPartecipationRequestProgramManagerRepository().save(pr);
    }

    public void removePR(Long idUser, Long tokenUser, Long idProgramManagerPR, String reason) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        PartecipationRequest<ProgramManagerRole> pr =
                repositoryHandler.getPartecipationRequestProgramManagerRepository().findById(idProgramManagerPR).get();
        user.getRolesHandler(tokenUser).getProjectProposerRole().removePR(pr, reason);
        repositoryHandler.getUserRepository().save(user);
        repositoryHandler.getPartecipationRequestProgramManagerRepository().save(pr);
    }
}
