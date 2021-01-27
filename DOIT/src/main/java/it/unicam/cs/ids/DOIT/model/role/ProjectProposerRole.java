package it.unicam.cs.ids.DOIT.model.role;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.user.User;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProjectProposerRole extends Role implements IPartecipationRequestHandler<ProgramManagerRole> {

    public final static String TYPE = "project-proposer";

    @Id
    @Column(name = "ID_ProjectProposer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProjectProposerRole() {
    }

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void createProject(String name, String description, Category category, ProjectState projectState)
            throws IllegalArgumentException {
        Project project = new Project(name, description, getInnerCategory(category), projectState);
        Team team = new Team(project, this);
        project.setTeam(team);
        this.getProjects().add(project);
    }

    @Override
    public void acceptPR(PartecipationRequest<ProgramManagerRole> programManagerPR) {
        PartecipationRequest<ProgramManagerRole> pr = getInnerProgramManagerRequest(programManagerPR);
        if (!this.getProjects().contains(pr.getProject()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il progetto");
        pr.displayed("Congratulations! You are accepted.");
        pr.getProject().getTeam().getProgramManagerRequest().remove(pr);
        pr.getProject().getTeam().setProgramManager(pr.getPendingRole());
        pr.getPendingRole().enterProject(pr.getProject());
        pr.getProject().getTeam().getProgramManagerRequest().stream()
                .filter(p -> !p.equals(pr))
                .forEach(p -> removePR(pr, "I'm sorry! You are rejected."));
    }

    @Override
    public void removePR(PartecipationRequest<ProgramManagerRole> programManagerPR, String description) {
        PartecipationRequest pr = getInnerProgramManagerRequest(programManagerPR);
        if (!this.getProjects().contains(pr.getProject()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il progetto");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getProject().getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getPartecipationRequestsByProject(Project inputProject) {
        Project project = getInnerProject(inputProject);
        if (!getProjects().contains(project))
            throw new IllegalArgumentException("Project non posseduto: [" + project.getId() + "]");
        return project.getTeam().getProgramManagerRequest();
    }
}