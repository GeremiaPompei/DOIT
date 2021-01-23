package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ProjectProposerRole extends Role implements IPartecipationRequestHandler {

    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    public ProjectProposerRole(IUser user, ICategory category) {
        super(user, category);
    }

    public void createProject(String name, String description, String idCategory)
            throws IllegalArgumentException {
        IProject project = servicesHandler.getFactoryModel().createProject(name, description, getInnerCategory(idCategory));
        ITeam team = servicesHandler.getFactoryModel().createTeam(project, this);
        this.getTeams().add(team);
    }

    @Override
    public void acceptPR(Long idProgramManager, Long idProject) throws RoleException {
        IPartecipationRequest pr = getInnerProgramManagerRequest(idProgramManager, idProject);
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
        IPartecipationRequest pr = getInnerDesignerRequest(idProgramManager, idProject);
        if (!this.getTeams().contains(pr.getTeam()))
            throw new IllegalArgumentException("Il Project Proposer non possiede il team]");
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("La descrizione non può essere vuota!");
        pr.displayed(description);
        pr.getTeam().getProgramManagerRequest().remove(pr);
    }

    public Set<IPartecipationRequest> getPartecipationRequestsByTeam(Long idProject) {
        ITeam team = getInnerTeam(idProject);
        if (!getTeams().contains(team))
            throw new IllegalArgumentException("Team non posseduto: [" + team.getId() + "]");
        return team.getProgramManagerRequest();
    }

    @Override
    public String toString() {
        return "ProjectProposerRole{role=" + super.toString() + "}";
    }
}