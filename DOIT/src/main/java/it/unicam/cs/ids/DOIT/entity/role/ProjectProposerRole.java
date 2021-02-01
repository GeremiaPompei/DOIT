package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.util.Set;

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
        project.setTeam(new Team(this));
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
        pr.getPendingRole().notify(pr.getDescription());
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
        pr.getPendingRole().notify(pr.getDescription());
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getPartecipationRequestsByProject(Project inputProject) {
        Project project = getInnerProject(inputProject);
        if (!getProjects().contains(project))
            throw new IllegalArgumentException("Project non posseduto: [" + project.getId() + "]");
        return project.getTeam().getProgramManagerRequest();
    }

    public void removeProject(Project project) {
        if(project.getTeam().getProgramManager()!=null)
            throw new IllegalArgumentException("Il progetto non è eliminabile, in quanto ha già assegnato un program manager" +
                    "con id ["+ project.getTeam().getProgramManager().getIdUser()+"]");
        Project myProject = getInnerProject(project);
        getProjects().remove(myProject);
        project.clearInitProject();
    }
}