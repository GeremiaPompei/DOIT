package it.unicam.cs.ids.DOIT.designer;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.repository.*;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.role.CVUnit;
import it.unicam.cs.ids.DOIT.entity.role.DesignerRole;
import it.unicam.cs.ids.DOIT.entity.role.Evaluation;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DesignerService {
    @Autowired
    private RepositoryHandler repositoryHandler;

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Project project = repositoryHandler.getProjectRepository().findById(projectId).get();
        PartecipationRequest<DesignerRole> pr =
                user.getRolesHandler(tokenUser).getDesignerRole().createPartecipationRequest(project);
        repositoryHandler.getPartecipationRequestDesignerRepository().save(pr);
        repositoryHandler.getUserRepository().save(user);
    }

    public Set<PartecipationRequest<DesignerRole>> listPR(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getDesignerRole().getMyPartecipationRequests();
    }

    public Set<Project> listProjectsByCategory(Long idUser, Long tokenUser, String idCategory) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        return user.getRolesHandler(tokenUser).getDesignerRole()
                .getProjectsByCategory(repositoryHandler.getProjectRepository().findAll().iterator(), category);
    }

    public Set<Evaluation> listEvaluations(Long idUser, Long tokenUser) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        return user.getRolesHandler(tokenUser).getDesignerRole().getEvaluations();
    }

    public void removeProject(Long iduser, Long tokenuser, Long idproject) {
        User user = repositoryHandler.getUserRepository().findById(iduser).get();
        Project project = repositoryHandler.getProjectRepository().findById(idproject).get();
        user.getRolesHandler(tokenuser).getDesignerRole().removeProject(project);
        repositoryHandler.getUserRepository().save(user);
    }

    public void insertPregressExperience(Long iduser, Long tokenuser, String pregressExperience, String dateStart, String dateFinish) {
        LocalDate dateStartLocalDate = LocalDate.parse(dateStart);
        LocalDate dateFinishLocalDate = LocalDate.parse(dateFinish);
        User user = repositoryHandler.getUserRepository().findById(iduser).get();
        user.getRolesHandler(tokenuser).getDesignerRole().insertPregressExperience(pregressExperience, dateStartLocalDate, dateFinishLocalDate);
        repositoryHandler.getUserRepository().save(user);
    }

    public List<CVUnit> visualizePregressExperiences(Long iduser, Long tokenuser) {
        User user = repositoryHandler.getUserRepository().findById(iduser).get();
        return user.getRolesHandler(tokenuser).getDesignerRole().getCurriculumVitae().stream().collect(Collectors.toList());
    }

    public void removePR(Long idUser, Long tokenUser, Long idPr) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        PartecipationRequest<DesignerRole> pr = repositoryHandler.getPartecipationRequestDesignerRepository()
                .findById(idPr).get();
        user.getRolesHandler(tokenUser).getDesignerRole().removeMyPr(pr);
        repositoryHandler.getPartecipationRequestDesignerRepository().delete(pr);
        repositoryHandler.getProjectRepository().save(pr.getProject());
        repositoryHandler.getUserRepository().save(user);
    }

}
