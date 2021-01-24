package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.service.FactoryModel;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProjectProposerRole extends Role implements IPartecipationRequestHandler {

    @Transient
    private IFactoryModel factoryModel = FactoryModel.getInstance();

    public ProjectProposerRole(User user, Category category) {
        super(user, category);
    }

    public void createProject(String name, String description, String idCategory)
            throws IllegalArgumentException {
        Project project = factoryModel.createProject(name, description, getInnerCategory(idCategory));
        Team team = factoryModel.createTeam(project, this);
        this.getTeams().add(team);
    }

    @Override
    public void acceptPR(Long idProgramManager, Long idProject) throws RoleException {
        PartecipationRequest pr = getInnerProgramManagerRequest(idProgramManager, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il team");
        pr.displayed("Congratulations! You are accepted.");
        pr.getTeam().getProgramManagerRequest().remove(pr);
        ProgramManagerRole pmRole = pr.getPendingRole().getUser().getRole(ProgramManagerRole.class);
        pr.getTeam().setProgramManager(pmRole);
        pr.getTeam().getProgramManagerRequest().stream()
                .filter(p -> !p.equals(pr))
                .forEach(p -> removePR(p.getPendingRole().getUser().getId(),
                        idProject, "I'm sorry! You are rejected."));
    }

    @Override
    public void removePR(Long idProgramManager, Long idProject, String description) {
        PartecipationRequest pr = getInnerDesignerRequest(idProgramManager, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<PartecipationRequest> getPartecipationRequestsByTeam(Long idProject) {
        Team team = getInnerTeam(idProject);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getProgramManagerRequest();
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }
}