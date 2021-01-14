package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.simple.model.roles.DesignerRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private boolean state;
    private IProject project;
    private IUser programManager;
    private Set<IUser> designers;
    private Set<IPartecipationRequest> partecipationRequests;

    public Team(IProject project, IUser programManager) {
        this.state = false;
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
        designer.getRole(DesignerRole.class).enterProject(this.project);
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
    public void openRegistrations() {
        this.state = true;
    }

    @Override
    public void closeRegistrations() {
        this.state = false;
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