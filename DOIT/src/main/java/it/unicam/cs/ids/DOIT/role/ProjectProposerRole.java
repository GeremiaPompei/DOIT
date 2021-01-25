package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProjectProposerRole extends Role implements IPartecipationRequestHandler<ProgramManagerRole> {

    @Id
    @Column(name = "ID_ProjectProposer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProjectProposerRole() {
    }

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public void createProject(String name, String description, Category category, ProjectState projectState)
            throws IllegalArgumentException {
        Project project = new Project(name, description, getInnerCategory(category), projectState);
        Team team = new Team(project, this);
        project.setTeam(team);
        this.getProjects().add(project);
    }

    @Override
    public void acceptPR(ProgramManagerRole role, Project inputProject) {
        PartecipationRequest<ProgramManagerRole> pr = getInnerProgramManagerRequest(role, inputProject);
        if (!this.getProjects().stream().map(p->p.getTeam()).collect(Collectors.toSet()).contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il progetto");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getProgramManagerRequest().remove(pr);
        pr.getTeam().setProgramManager(role);
        role.enterProject(inputProject);
        pr.getTeam().getProgramManagerRequest().stream()
                .filter(p -> !p.equals(pr))
                .forEach(p -> removePR(p.getPendingRole(),
                        inputProject, "I'm sorry! You are rejected."));
    }

    @Override
    public void removePR(ProgramManagerRole role, Project inputProject, String description) {
        PartecipationRequest pr = getInnerProgramManagerRequest(role, inputProject);
        if (!this.getProjects().stream().map(p->p.getTeam()).collect(Collectors.toSet()).contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il progetto");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getPartecipationRequestsByProject(Project inputProject) {
        Project project = getInnerProject(inputProject);
        if (!getProjects().contains(project))
            throw new IllegalArgumentException("Project non posseduto: [" + project.getId() + "]");
        return project.getTeam().getProgramManagerRequest();
    }
}