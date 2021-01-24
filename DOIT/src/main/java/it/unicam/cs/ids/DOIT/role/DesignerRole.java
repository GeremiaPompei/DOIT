package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.service.FactoryModel;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DesignerRole extends PendingRole {

    private IFactoryModel factoryModel = FactoryModel.getInstance();
    private Set<PartecipationRequest> partecipationRequests;
    private Map<Team, Integer> evaluations;
    private Map<LocalDate, String> curriculumVitae;

    public DesignerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashMap<>();
        this.evaluations = new HashMap<>();
    }

    public Set<PartecipationRequest> getMyPartecipationRequests() {
        return partecipationRequests;
    }

    public void createPartecipationRequest(Team team) {
        getInnerCategory(team.getProject().getCategory().getName());
        if (team.getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        if (!team.isOpen())
            throw new IllegalArgumentException("Le registrazioni non sono aperte !");
        PartecipationRequest pr = factoryModel.createPartecipationRequest(this, team);
        if (this.partecipationRequests.contains(pr))
            this.partecipationRequests.remove(pr);
        this.partecipationRequests.add(pr);
        if (team.getDesignerRequest().contains(pr))
            team.getDesignerRequest().remove(pr);
        team.getDesignerRequest().add(pr);
    }

    public Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category) {
        Set<Project> projects = new HashSet<>();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            if (project.getCategory().equals(category) && project.getTeam().isOpen())
                projects.add(project);
        }
        return projects;
    }

    public void enterEvaluation(Long idProject, int evaluation) {
        this.evaluations.put(getInnerTeam(idProject), evaluation);
    }

    public Map<Team, Integer> getEvaluations() {
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