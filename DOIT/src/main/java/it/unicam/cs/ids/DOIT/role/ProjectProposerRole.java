package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProjectProposerRole extends Role implements IPartecipationRequestHandler<ProgramManagerRole> {

    @Id
    @Column(name = "ID_ProjectProposer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProjectProposerRole(User user, Category category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
    }

    public void createProject(String name, String description, Category category, ProjectState projectState)
            throws IllegalArgumentException {
        Project project = factoryModel.createProject(name, description, getInnerCategory(category), projectState);
        Team team = factoryModel.createTeam(project, this);
        this.getTeams().add(team);
    }

    @Override
    public void acceptPR(ProgramManagerRole role, Team team) {
        PartecipationRequest<ProgramManagerRole> pr = getInnerProgramManagerRequest(role, team);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getProgramManagerRequest().remove(pr);
        pr.getTeam().setProgramManager(role);
        pr.getTeam().getProgramManagerRequest().stream()
                .filter(p -> !p.equals(pr))
                .forEach(p -> removePR(p.getPendingRole(),
                        team, "I'm sorry! You are rejected."));
    }

    @Override
    public void removePR(ProgramManagerRole role, Team team, String description) {
        PartecipationRequest pr = getInnerProgramManagerRequest(role, team);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<PartecipationRequest<ProgramManagerRole>> getPartecipationRequestsByTeam(Team teamInput) {
        Team team = getInnerTeam(teamInput);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getProgramManagerRequest();
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }
}