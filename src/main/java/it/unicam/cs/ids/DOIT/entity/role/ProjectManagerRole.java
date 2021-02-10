package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ProjectManagerRole extends Role {
    public final static String TYPE = "project-manager";

    @Id
    @Column(name = "id_project_manager")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProjectManagerRole() {
    }

    public ProjectManagerRole(User user, Category category) {
        super(user, category);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void upgradeState(Iterator<ProjectState> iterator, Project project) {
        ProjectState ps = findProjectState(iterator, project.getProjectState().getId() + 1);
        if (ps == null)
            throw new IllegalArgumentException("This is the last state of the project!");
        project.setProjectState(ps);
        ProjectState nextps = findProjectState(iterator, project.getProjectState().getId() + 1);
        if (nextps == null)
            project.getTeam().closeRegistrations();
        notifyAll("Upgrade state: [" + ps.getName() + "] of project: [" + project.getName() + "]", project);
    }

    public void downgradeState(Iterator<ProjectState> iterator, Project project) {
        ProjectState ps = findProjectState(iterator, project.getProjectState().getId() - 1);
        if (ps == null)
            throw new IllegalArgumentException("This is the first state of the project!");
        project.setProjectState(ps);
        notifyAll("Downgrade state: [" + ps.getName() + "] of project: [" + project.getName() + "]", project);
    }

    private ProjectState findProjectState(Iterator<ProjectState> iterator, Long id) {
        while (iterator.hasNext()) {
            ProjectState projectState = iterator.next();
            if (projectState.getId().equals(id))
                return projectState;
        }
        return null;
    }

    public Evaluation insertEvaluation(User user, Project projectInput, int evaluation) {
        Project project = getInnerProject(projectInput);
        DesignerRole designerFound = getInnerDesignerInTeam(user, project);
        if (evaluation < 0 || evaluation > 5)
            throw new IllegalArgumentException("The evaluation must be between 0 and 5!");
        Evaluation ev = new Evaluation(project.getId(), evaluation);
        designerFound.getEvaluations().add(ev);
        designerFound.notify("Evaluate: [" + evaluation + "] and exit project: [" + project.getName() + "]");
        designerFound.exitProject(project);
        return ev;
    }

    public Set<Long> getDesigners(Project projectInput) {
        return getInnerProject(projectInput).getTeam().getDesigners().stream().map(t -> t.getIdUser()).collect(Collectors.toSet());
    }

    public void exitAll(Project projectInput) {
        Project project = getInnerProject(projectInput);
        for (DesignerRole d : project.getTeam().getDesigners())
            if (d.getProjects().contains(project))
                throw new IllegalArgumentException("Before closing the project u must evaluate all the designers!");
        notifyAll("Clossing project: [" + project.getName() + "]", project);
        project.getTeam().getProgramManagerRequest().removeAll(project.getTeam().getProgramManagerRequest());
        project.getTeam().getDesignerRequest().removeAll(project.getTeam().getDesignerRequest());
        project.getTeam().getProjectProposer().exitProject(project);
        project.getTeam().getProgramManager().exitProject(project);
        project.getTeam().getProjectManager().exitProject(project);
        project.getTeam().closeRegistrations();
    }

    public ProjectState getProjectState(Project projectInput) {
        return this.getProjects().stream().filter(t -> t.equals(projectInput)).findAny().orElse(null).getProjectState();
    }

    private void notifyAll(String notification, Project project) {
        project.getTeam().getProjectProposer().notify(notification);
        project.getTeam().getProgramManager().notify(notification);
        project.getTeam().getDesigners().forEach(d -> d.notify(notification));
    }

    public void removeProject(User thisUser, User nextprojectmanageruser, Project project) {
        if (!project.getTeam().getDesigners().contains(nextprojectmanageruser.getRolesHandler().getDesignerRole()))
            throw new IllegalArgumentException("This project doesn't have  [" + nextprojectmanageruser.getId() + "]");
        if (!(project.getTeam().getProjectManager().equals(thisUser.getRolesHandler().getProjectManagerRole())))
            throw new IllegalArgumentException("The team doesn't have the same project manager[" + thisUser.getId() + "]");
        Team team = project.getTeam();
        this.getProjects().remove(project);
        DesignerRole newDesigner = thisUser.getRolesHandler().getDesignerRole();
        DesignerRole oldDesigner = nextprojectmanageruser.getRolesHandler().getDesignerRole();
        newDesigner.enterProject(project);
        team.getDesigners().add(newDesigner);
        oldDesigner.getProjects().remove(project);
        team.getDesigners().remove(oldDesigner);
        Category category = project.getCategory();
        if (!nextprojectmanageruser.getRolesHandler().isProjectManager())
            nextprojectmanageruser.getRolesHandler().addRole(ProjectManagerRole.TYPE, category);
        ProjectManagerRole newProjectManager = nextprojectmanageruser.getRolesHandler().getProjectManagerRole();
        newProjectManager.enterProject(project);
        team.setProjectManager(newProjectManager);
        newProjectManager.notify("You have been chosen as a project manager by: [" + thisUser.getName() + "]");
    }
}
