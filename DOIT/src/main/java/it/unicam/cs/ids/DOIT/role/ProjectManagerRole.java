package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectManagerRole extends Role {

    public ProjectManagerRole(IUser user, ICategory category) {
        super(user, category);
    }

    public void upgradeState(int idProject) {
        ITeam team = getInnerTeam(idProject);
        ProjectState ps = ServicesHandler.getInstance().getResourceHandler().getProjectState(team.getProject().getProjectState().getId() + 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto terminale, non si può procedere oltre!");
        team.getProject().setProjectState(ps);
    }

    public void downgradeState(int idProject) {
        ITeam team = getInnerTeam(idProject);
        ProjectState ps = ServicesHandler.getInstance().getResourceHandler().getProjectState(team.getProject().getProjectState().getId() - 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto iniziale, non si può procedere oltre!");
        team.getProject().setProjectState(ps);

    }

    public void insertEvaluation(int idDesigner, int idProject, int evaluation) throws RoleException {
        DesignerRole designer = getInnerDesignerInTeam(idProject, idDesigner);
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 0 e 5!");
        designer.enterEvaluation(idProject, evaluation);
        designer.exitTeam(idProject);
    }

    public Set<IUser> getDesigners(int idProject) {
        return getInnerTeam(idProject).getDesigners().stream().map(t -> t.getUser()).collect(Collectors.toSet());
    }

    public void exitAll(int idProject) {
        ITeam team = getInnerTeam(idProject);
        for (DesignerRole d : team.getDesigners())
            if (d.getTeams().contains(team))
                throw new IllegalArgumentException("Prima di chiudere il progetto finisci di valutare i designer!");
        team.getProjectProposer().exitTeam(team.getId());
        team.getProgramManager().exitTeam(team.getId());
        team.getProjectManager().exitTeam(team.getId());
    }

    public ProjectState getProjectState(int idProject) {
        return this.getTeams().stream().filter(t -> t.getId() == idProject).findAny().orElse(null).getProject().getProjectState();
    }

    @Override
    public String toString() {
        return "ProjectManagerRole{role=" + super.toString() + "}";
    }
}
