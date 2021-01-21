package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DesignerRole extends Role implements IPendingRole {

    private Set<IPartecipationRequest> partecipationRequests;
    private Map<ITeam, Integer> evaluations;
    private Map<LocalDate, String> curriculumVitae;

    public DesignerRole(IUser user, ICategory category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashMap<>();
        this.evaluations = new HashMap<>();
    }

    public Set<IPartecipationRequest> getMyPartecipationRequests() {
        return partecipationRequests;
    }

    public void createPartecipationRequest(Long idProject) {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        getInnerCategory(team.getProject().getCategory().getName());
        if (team.getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        if (!team.isOpen())
            throw new IllegalArgumentException("Le registrazioni non sono aperte !");
        IPartecipationRequest pr = ServicesHandler.getInstance().getFactoryModel().createPartecipationRequest(this, team);
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (team.getDesignerRequest().contains(pr))
            team.getDesignerRequest().remove(pr);
        team.getDesignerRequest().add(pr);
    }

    public Set<IProject> getProjectsByCategory(String idCategory) {
        return ServicesHandler.getInstance().getResourceHandler().getProjectsByCategory(idCategory).stream()
                .filter(p -> p.getTeam().isOpen()).collect(Collectors.toSet());
    }

    public void enterEvaluation(Long idProject, int evaluation) {
        this.evaluations.put(getInnerTeam(idProject), evaluation);
    }

    public Map<ITeam, Integer> getEvaluations() {
        return evaluations;
    }

    public Map<LocalDate, String> getCurriculumVitae() {
        return curriculumVitae;
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