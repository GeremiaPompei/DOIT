package it.unicam.cs.ids.DOIT.model.role;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.user.User;

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

    public ProjectManagerRole() {
    }

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
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

    public void insertEvaluation(User user, Project projectInput, int evaluation) {
        DesignerRole designerFound = getInnerDesignerInTeam(user, projectInput);
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 0 e 5!");
        designerFound.enterEvaluation(projectInput, evaluation);
        designerFound.exitProject(projectInput);
    }

    public Set<Long> getDesigners(Project projectInput) {
        return getInnerProject(projectInput).getTeam().getDesigners().stream().map(t -> t.getIdUser()).collect(Collectors.toSet());
    }

    public void exitAll(Project projectInput) {
        Project project = getInnerProject(projectInput);
        for (DesignerRole d : project.getTeam().getDesigners())
            if (d.getProjects().contains(project))
                throw new IllegalArgumentException("Prima di chiudere il progetto finisci di valutare i designer!");
        project.getTeam().getProjectProposer().exitProject(project);
        project.getTeam().getProgramManager().exitProject(project);
        project.getTeam().getProjectManager().exitProject(project);
        project.getTeam().closeRegistrations();
    }

    public ProjectState getProjectState(Project projectInput) {
        return this.getProjects().stream().filter(t -> t.equals(projectInput)).findAny().orElse(null).getProjectState();
    }

    @Override
    public String toString() {
        return "ProjectManagerRole{role=" + super.toString() + "}";
    }
}
