package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DesignerRole extends Role implements IPendingRole {

    private Set<IPartecipationRequest> partecipationRequests;
    //TODO aggiungere informazioni del designer su esperienze pregresse.
    private Map<LocalDate, String> curriculumVitae;
    private Map<ITeam, Integer> evaluations;


    public DesignerRole(Integer idUser, String idCategory) {
        super(idUser, idCategory);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashMap<>();
        this.evaluations = new HashMap<>();
    }

    public Set<IPartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    public void createPartecipationRequest(int idProject) throws RoleException {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        if (team.getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        if (!team.getState())
            throw new IllegalArgumentException("Le registrazioni non sono aperte !");
        IPartecipationRequest partecipationRequest = ServicesHandler.getInstance().getFactoryModel()
                .createPartecipationRequest(this, team);
        this.partecipationRequests.add(partecipationRequest);
        team.getDesignerRequest().add(partecipationRequest);
    }

    public Set<IProject> getProjects(String idCategory) {
        return ServicesHandler.getInstance().getResourceHandler().getProjectsByCategory(idCategory).stream()
                .filter(p -> p.getTeam().getState()).collect(Collectors.toSet());
    }

    public void enterEvaluation(int idProject, int evaluation) {
        this.evaluations.put(ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam(), evaluation);
    }

    public Map<ITeam, Integer> getEvaluations() {
        return evaluations;
    }

    @Override
    public String toString() {
        return "DesignerRole{" +
                "partecipationRequests=" + partecipationRequests +
                ", curriculumVitae=" + curriculumVitae +
                ", evaluations=" + evaluations.entrySet().stream().map(t -> t.getKey().getId() + "-" + t.getValue()).
                reduce((x, y) -> x + ", " + y) +
                ", role=" + super.toString() +
                '}';
    }
}