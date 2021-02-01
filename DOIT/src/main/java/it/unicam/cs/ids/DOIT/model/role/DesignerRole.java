package it.unicam.cs.ids.DOIT.model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class DesignerRole extends Role implements IPendingRole<DesignerRole> {

    public final static String TYPE = "designer";

    @Id
    @Column(name = "ID_Designer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"pendingRole", "team"})
    private Set<PartecipationRequest<DesignerRole>> myPartecipationRequests;

    @JoinColumn(name = "ID_Evaluation")
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Evaluation> evaluations;
    @JoinColumn(name = "ID_CVUnit")
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<CVUnit> curriculumVitae;

    public DesignerRole() {
    }

    public DesignerRole(User user, Category category) {
        super(user, category);
        this.myPartecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashSet<>();
        this.evaluations = new HashSet<>();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Set<PartecipationRequest<DesignerRole>> getMyPartecipationRequests() {
        return myPartecipationRequests;
    }

    public void removeMyPr(PartecipationRequest<DesignerRole> pr) {
        this.myPartecipationRequests.remove(pr);
        pr.getProject().getTeam().getDesignerRequest().remove(pr);
    }

    public PartecipationRequest<DesignerRole> createPartecipationRequest(Project inputProject) {
        getInnerCategory(inputProject.getCategory());
        if (inputProject.getTeam().getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (inputProject.getTeam().getDesigners().contains(this))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (inputProject.getTeam().getProjectManager() != null &&
                inputProject.getTeam().getProjectManager().getIdUser().equals(this.id))
            throw new IllegalArgumentException("Designer gia presente nel team come Project Manager!");
        if (!this.getCategories().contains(inputProject.getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" + inputProject.getCategory() + "]");
        if (!inputProject.getTeam().isOpen())
            throw new IllegalArgumentException("Le registrazioni non sono aperte !");
        PartecipationRequest<DesignerRole> pr = new PartecipationRequest(this, inputProject);
        if (this.myPartecipationRequests.contains(pr))
            this.myPartecipationRequests.remove(pr);
        this.myPartecipationRequests.add(pr);
        if (inputProject.getTeam().getDesignerRequest().contains(pr))
            inputProject.getTeam().getDesignerRequest().remove(pr);
        inputProject.getTeam().getDesignerRequest().add(pr);
        inputProject.getTeam().getProgramManager().notify(pr.getDescription());
        inputProject.getTeam().getProgramManager().notify("Qualcuno vuole partecipare al progetto: [" +
                inputProject.getName() + "]");
        return pr;
    }

    public Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category) {
        Set<Project> projects = new HashSet<>();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            if (project.getCategory().equals(getInnerCategory(category)) && project.getTeam().isOpen() &&
                    !project.getTeam().getDesignerRequest().stream().map(r -> r.getPendingRole())
                            .collect(Collectors.toSet()).contains(this) &&
                    !project.getTeam().getDesigners().contains(this) && (project.getTeam().getProjectManager() == null
                    || project.getTeam().getProjectManager() != null && !project.getTeam().getProjectManager().getIdUser().equals(this.getIdUser())))
                projects.add(project);
        }
        return projects;
    }

    public void removeProject(Project project) {
        Project myProject = getInnerProject(project);
        this.getProjects().remove(myProject);
        project.getTeam().getDesigners().remove(this);
        project.getTeam().getProgramManager().notify("Un designer si è eliminato dal team.");
    }

    public void insertPregressExperience(String pregressExperience, LocalDate dateStart, LocalDate dateFinish) {
        CVUnit newCVUnit = new CVUnit(dateStart, dateFinish, pregressExperience);
        curriculumVitae.add(newCVUnit);
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public Set<CVUnit> getCurriculumVitae() {
        return curriculumVitae;
    }
}