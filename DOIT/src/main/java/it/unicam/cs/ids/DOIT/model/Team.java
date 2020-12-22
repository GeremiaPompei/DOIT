package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.Roles.DesignerRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private boolean state = false;
    private int id;
    private Project project;
    private User programManager;
    private List<User> designer;
    private List<PartecipationRequest> partecipationRequests;

    public Team(int id, Project project, User programManager) {
        this.id = id;
        this.project = project;
        this.programManager = programManager;
        this.designer = new ArrayList<>();
        this.partecipationRequests = new ArrayList<>();
    }

    public boolean getState() {
        return state;
    }

    public int getId() {
        return id;
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

    public List<User> getDesigners() {
        return designer;
    }

    public List<PartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    @Override
    public String toString() {
        return "Team{" +
                "state=" + state +
                ", id=" + id +
                ", project=" + project.getId() +
                ", programManager=" + programManager.getId() +
                ", designer=" + designer.stream().map(d -> d.getId()).collect(Collectors.toSet()) +
                ", partecipationRequests=" + partecipationRequests +
                '}';
    }
}