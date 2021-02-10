package it.unicam.cs.ids.DOIT.entity.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class DesignerRole extends Role implements IPendingRole<DesignerRole> {

    public final static String TYPE = "designer";

    @Id
    @Column(name = "id_designer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"pendingRole", "team"})
    private Set<PartecipationRequest<DesignerRole>> myPartecipationRequests;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Evaluation> evaluations;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CVUnit> curriculumVitae;

    public DesignerRole() {
    }

    public DesignerRole(User user, Category category) {
        super(user, category);
        this.myPartecipationRequests = new HashSet<>();
        this.curriculumVitae = new ArrayList<>();
        this.evaluations = new HashSet<>();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Set<PartecipationRequest<DesignerRole>> myPartecipationRequests() {
        return myPartecipationRequests;
    }

    public void removeMyPr(PartecipationRequest<DesignerRole> pr) {
        this.myPartecipationRequests.remove(pr);
        pr.getProject().getTeam().getDesignerRequest().remove(pr);
    }

    public PartecipationRequest<DesignerRole> createPartecipationRequest(Project inputProject) {
        getInnerCategory(inputProject.getCategory());
        if (inputProject.getTeam().getDesignerRequest().stream().map(p -> p.getPendingRole()).collect(Collectors.toSet()).contains(this))
            throw new IllegalArgumentException("You already sent a partecipation request to this team");
        if (inputProject.getTeam().getDesigners().contains(this))
            throw new IllegalArgumentException("This designer is already in the team");
        if (inputProject.getTeam().getProjectManager() != null &&
                inputProject.getTeam().getProjectManager().getIdUser().equals(this.id))
            throw new IllegalArgumentException("This designer is alrady in the team as a project manager");
        if (!this.getCategories().contains(inputProject.getCategory()))
            throw new IllegalArgumentException("This user doesnt have the category: [" + inputProject.getCategory() + "]");
        if (!inputProject.getTeam().isOpen())
            throw new IllegalArgumentException("Registrations are still not open!");
        PartecipationRequest<DesignerRole> pr = new PartecipationRequest(this, inputProject);
        if (this.myPartecipationRequests.contains(pr))
            this.myPartecipationRequests.remove(pr);
        this.myPartecipationRequests.add(pr);
        if (inputProject.getTeam().getDesignerRequest().contains(pr))
            inputProject.getTeam().getDesignerRequest().remove(pr);
        inputProject.getTeam().getDesignerRequest().add(pr);
        inputProject.getTeam().getProgramManager().notify(pr.getDescription());
        inputProject.getTeam().getProgramManager().notify("Someone sent a partecipation request: [" +
                inputProject.getName() + "]");
        return pr;
    }

    public Set<Project> projectsByCategory(Iterator<Project> iterator, Category category) {
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
        project.getTeam().getProgramManager().notify("A designer left the team");
    }

    public void insertPregressExperience(String pregressExperience, LocalDate dateStart, LocalDate dateFinish) {
        if (pregressExperience == "")
            throw new IllegalArgumentException("Insert previous experience!");
        if (dateStart.isAfter(dateFinish))
            throw new IllegalArgumentException("The end date can't be before the start date!");
        CVUnit newCVUnit = new CVUnit(dateStart, dateFinish, pregressExperience);
        curriculumVitae.add(newCVUnit);
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public List<CVUnit> getCurriculumVitae() {
        return curriculumVitae;
    }
}