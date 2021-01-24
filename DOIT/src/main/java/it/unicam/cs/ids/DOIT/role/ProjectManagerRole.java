package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectManagerRole extends Role {

    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
    }

    public void upgradeState(Long idProject) {
        Team team = getInnerTeam(idProject);
        ProjectState ps = servicesHandler.getResourceHandler().getProjectState(team.getProject().getProjectState().getId() + 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto terminale, non si può procedere oltre!");
        team.getProject().setProjectState(ps);
    }

    public void downgradeState(Long idProject) {
        Team team = getInnerTeam(idProject);
        ProjectState ps = servicesHandler.getResourceHandler().getProjectState(team.getProject().getProjectState().getId() - 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto iniziale, non si può procedere oltre!");
        team.getProject().setProjectState(ps);

    }

    public void insertEvaluation(Long idDesigner, Long idProject, int evaluation) throws RoleException {
        DesignerRole designer = getInnerDesignerInTeam(idDesigner, idProject);
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 0 e 5!");
        designer.enterEvaluation(idProject, evaluation);
        designer.exitTeam(idProject);
    }

    public Set<User> getDesigners(Long idProject) {
        return getInnerTeam(idProject).getDesigners().stream().map(t -> t.getUser()).collect(Collectors.toSet());
    }

    public void exitAll(Long idProject) {
        Team team = getInnerTeam(idProject);
        for (DesignerRole d : team.getDesigners())
            if (d.getTeams().contains(team))
                throw new IllegalArgumentException("Prima di chiudere il progetto finisci di valutare i designer!");
        team.getProjectProposer().exitTeam(team.getId());
        team.getProgramManager().exitTeam(team.getId());
        team.getProjectManager().exitTeam(team.getId());
        team.closeRegistrations();
    }

    public ProjectState getProjectState(int idProject) {
        return this.getTeams().stream().filter(t -> t.getId() == idProject).findAny().orElse(null).getProject().getProjectState();
    }

    @Override
    public String toString() {
        return "ProjectManagerRole{role=" + super.toString() + "}";
    }
}
