package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Team implements  ITeam{
    private boolean state = false;
    private IProject project;
    private IUser programManager;
    private Set<IUser> designers;
    private Set<IPartecipationRequest> partecipationRequests;

    public Team(IProject project, IUser programManager) {
        this.project = project;
        this.programManager = programManager;
        this.designers = new HashSet<>();
        this.partecipationRequests = new HashSet<>();
    }

    public boolean getState() {
        return state;
    }

    public IProject getProject() {
        return project;
    }

    public IUser getProgramManager() {
        return programManager;
    }

    public void addDesigner(IUser designer) throws RoleException {
        designer.getRole(DesignerRole.class).addProject(this.project);
        this.designers.add(designer);
    }

    public void removeDesigner(IUser designer) throws RoleException {
        designer.getRole(DesignerRole.class).getProjects().remove(this.project);
        this.designers.remove(designer);
    }

    public Set<IUser> getDesigners() {
        return designers;
    }

    public Set<IPartecipationRequest> getPartecipationRequests() {
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