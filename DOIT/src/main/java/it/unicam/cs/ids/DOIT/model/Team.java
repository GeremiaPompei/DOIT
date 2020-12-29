package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.DesignerRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Team {
    private boolean state = false;
    private Project project;
    private User programManager;
    private Set<User> designers;
    private Set<PartecipationRequest> partecipationRequests;

    public Team(Project project, User programManager) {
        this.project = project;
        this.programManager = programManager;
        this.designers = new HashSet<>();
        this.partecipationRequests = new HashSet<>();
    }

    public boolean getState() {
        return state;
    }

    public Project getProject() {
        return project;
    }

    public User getProgramManager() {
        return programManager;
    }

    public void addDesigner(User designer) throws RoleException {
        designer.getRole(DesignerRole.class).getCurriculumVitae().enterProject(this.project);
        designer.getRole(DesignerRole.class).getProjects().add(this.project);
        this.designers.add(designer);
    }

    public void removeDesigner(User designer) throws RoleException {
        designer.getRole(DesignerRole.class).getCurriculumVitae().exitProject(this.project);
        designer.getRole(DesignerRole.class).getProjects().remove(this.project);
        this.designers.remove(designer);
    }

    public Set<User> getDesigners() {
        return designers;
    }

    public Set<PartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    @Override
    public String toString() {
        return "Team{" +
                "state=" + state +
                ", project=" + project.getId() +
                ", programManager=" + programManager.getId() +
                ", designer=" + designers.stream().map(d -> d.getId()).collect(Collectors.toSet()) +
                ", partecipationRequests=" + partecipationRequests +
                '}';
    }
}