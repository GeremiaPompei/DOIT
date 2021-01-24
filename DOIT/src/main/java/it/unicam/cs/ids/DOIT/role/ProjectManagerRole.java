package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProjectManagerRole extends Role {

    @Id
    @Column(name = "ID_ProjectManager")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public ProjectManagerRole(User user, Category category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
    }

    public void upgradeState(Iterator<ProjectState> iterator, Project project) {
        ProjectState ps = findProjectState(iterator, project.getProjectState().getId() + 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto terminale, non si può procedere oltre!");
        project.setProjectState(ps);
    }

    public void downgradeState(Iterator<ProjectState> iterator, Project project) {
        ProjectState ps = findProjectState(iterator, project.getProjectState().getId() - 1);
        if (ps == null)
            throw new IllegalArgumentException("Stato progetto iniziale, non si può procedere oltre!");
        project.setProjectState(ps);
    }

    private ProjectState findProjectState(Iterator<ProjectState> iterator, Long id) {
        while (iterator.hasNext()) {
            ProjectState projectState = iterator.next();
            if (projectState.getId().equals(id))
                return projectState;
        }
        return null;
    }

    public void insertEvaluation(DesignerRole designer, Team team, int evaluation) throws RoleException {
        DesignerRole designerFound = getInnerDesignerInTeam(designer, team);
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 0 e 5!");
        designerFound.enterEvaluation(team, evaluation);
        designerFound.exitTeam(team);
    }

    public Set<User> getDesigners(Team team) {
        return getInnerTeam(team).getDesigners().stream().map(t -> t.getUser()).collect(Collectors.toSet());
    }

    public void exitAll(Team team) {
        Team teamFound = getInnerTeam(team);
        for (DesignerRole d : teamFound.getDesigners())
            if (d.getTeams().contains(teamFound))
                throw new IllegalArgumentException("Prima di chiudere il progetto finisci di valutare i designer!");
        teamFound.getProjectProposer().exitTeam(teamFound);
        teamFound.getProgramManager().exitTeam(teamFound);
        teamFound.getProjectManager().exitTeam(teamFound);
        teamFound.closeRegistrations();
    }

    public ProjectState getProjectState(Team team) {
        return this.getTeams().stream().filter(t -> t.equals(team)).findAny().orElse(null).getProject().getProjectState();
    }

    @Override
    public String toString() {
        return "ProjectManagerRole{role=" + super.toString() + "}";
    }
}
