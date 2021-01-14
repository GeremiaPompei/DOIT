package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.IProjectState;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;

import java.util.Set;
import java.util.function.Function;

public class ProjectManagerRole extends Role implements IProjectManagerRole {

    public ProjectManagerRole(IUser user, ICategory category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
    }

    public Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return pr -> pr.stream().filter(p -> p.getId() == project.getProjectState().getId() + 1).findAny().orElse(null);
    }

    public Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return pr -> pr.stream().filter(p -> p.getId() == project.getProjectState().getId() - 1).findAny().orElse(null);

    }

    @Override
    public void insertEvaluation(IUser designer, int evaluation, IProject project) throws RoleException {
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 0 e 5!");
        designer.getRole(IDesignerRole.class).enterEvaluation(project, evaluation);
        designer.getRole(IDesignerRole.class).exitProject(project);
    }

    public Set<IUser> getDesigners(ITeam team) {
        if (!getProjects().contains(team.getProject()))
            throw new IllegalArgumentException("Team non presente: [" + team.getProject().getId() + "]");
        return team.getDesigners();
    }

    @Override
    public void exitAll(IProject project) throws RoleException {
        for (IUser d : project.getTeam().getDesigners())
            if (d.getRole(IDesignerRole.class).getProjects().contains(project))
                throw new IllegalArgumentException("Prima di chiudere il progetto finisci di valutare i designer!");
        project.getProjectProposer().getRole(IProjectProposerRole.class).exitProject(project);
        project.getTeam().getProgramManager().getRole(IProgramManagerRole.class).exitProject(project);
        project.getProjectManager().getRole(IProjectManagerRole.class).exitProject(project);
    }

    @Override
    public String toString() {
        return "ProjectManagerRole{role=" + super.toString() + "}";
    }
}
