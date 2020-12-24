package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.DesignerRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Team {
    private boolean state = false;
    private Project project;
    private User programManager;
    private Set<User> designer;
    private Set<PartecipationRequest> partecipationRequests;

    public Team(Project project, User programManager) {
        this.project = project;
        this.programManager = programManager;
        this.designer = new HashSet<>();
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

    public boolean addDesigner(User designer) throws RoleException {
        boolean b = designer.getRole(DesignerRole.class).getCurriculumVitae().enterProject(this.project.getId());
        return this.designer.add(designer) && b;
    }

    public boolean removeDesigner(User designer) throws RoleException {
        boolean b = designer.getRole(DesignerRole.class).getCurriculumVitae().exitProject(this.project.getId());
        return this.designer.remove(designer) && b;
    }

    public Set<User> getDesigners() {
        return designer;
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
                ", designer=" + designer.stream().map(d -> d.getId()).collect(Collectors.toSet()) +
                ", partecipationRequests=" + partecipationRequests +
                '}';
    }
}