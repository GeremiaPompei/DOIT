package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class DesignerRole extends PendingRole {

    @Id
    @Column(name = "ID_Designer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<PartecipationRequest> partecipationRequests;

    @JoinColumn(name = "ID_Evaluation")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Evaluation> evaluations;
    @JoinColumn(name = "ID_CVUnit")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<CVUnit> curriculumVitae;

    public DesignerRole() {
    }

    public DesignerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashSet<>();
        this.evaluations = new HashSet<>();
    }

    public Set<PartecipationRequest> getMyPartecipationRequests() {
        return partecipationRequests;
    }

    public void createPartecipationRequest(Team team) {
        getInnerCategory(team.getProject().getCategory());
        if (team.getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + team.getProject().getCategory() + "]");
        if (!team.isOpen())
            throw new IllegalArgumentException("Le registrazioni non sono aperte !");
        PartecipationRequest pr = new PartecipationRequest(this, team);
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
            if (project.getCategory().equals(getInnerCategory(category)) && project.getTeam().isOpen())
                projects.add(project);
        }
        return projects;
    }

    public void enterEvaluation(Team team, int evaluation) {
        Team teamFound = getInnerTeam(team);
        this.evaluations.add(new Evaluation(team, evaluation));
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public Set<CVUnit> getCurriculumVitae() {
        return curriculumVitae;
    }

    @Override
    public String toString() {
        return "DesignerRole{" +
                "partecipationRequests=" + partecipationRequests +
                ", curriculumVitae=" + curriculumVitae +
                ", evaluations=" + evaluations.stream().map(t -> t.getTeam().getId() + "-" + t.getEvaluate()).
                reduce((x, y) -> x + ", " + y) +
                ", role=" + super.toString() +
                '}';
    }
}