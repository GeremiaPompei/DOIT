package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private boolean state;
    private IProject project;
    private ProjectProposerRole projectProposer;
    private ProjectManagerRole projectManager;
    private ProgramManagerRole programManager;
    private Set<DesignerRole> designers;
    private Set<IPartecipationRequest> designerRequest;

    public Team(IProject project, ProjectProposerRole projectProposer) {
        this.state = false;
        this.project = project;
        this.project.setTeam(this);
        this.projectProposer = projectProposer;
        this.designers = new HashSet<>();
        this.designerRequest = new HashSet<>();
    }

    @Override
    public int getId() {
        return this.project.getId();
    }

    public boolean getState() {
        return state;
    }

    public ProgramManagerRole getProgramManager() {
        return programManager;
    }

    public void addDesigner(DesignerRole designer) {
        designer.enterTeam(this.project.getId());
        this.designers.add(designer);
    }

    public void removeDesigner(DesignerRole designer) {
        designer.getTeams().remove(this.project);
        this.designers.remove(designer);
    }

    public Set<DesignerRole> getDesigners() {
        return designers;
    }

    public Set<IPartecipationRequest> getDesignerRequest() {
        return designerRequest;
    }

    public void setProgramManager(ProgramManagerRole programManagerRole) {
        this.programManager = programManagerRole;
        programManagerRole.enterTeam(this.getId());
    }

    public void setProjectManager(ProjectManagerRole projectManager) {
        this.projectManager = projectManager;
    }

    public IRole getProjectProposer() {
        return projectProposer;
    }

    public IRole getProjectManager() {
        return projectManager;
    }

    @Override
    public void openRegistrations() {
        this.state = true;
    }

    @Override
    public void closeRegistrations() {
        this.state = false;
    }

    public boolean isState() {
        return state;
    }

    public IProject getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(project, team.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }

    @Override
    public String toString() {
        return "Team{" +
                "state=" + state +
                ", projectProposer=" + projectProposer.getId() +
                ", projectManager=" + (projectManager == null ? "null" : projectManager.getId()) +
                ", programManager=" + (programManager == null ? "null" : programManager.getId()) +
                ", designers=" + designers.stream().map(d -> d.getId()).collect(Collectors.toSet()) +
                ", designerRequest=" + designerRequest.stream().map(pr -> pr.getPendingRole().getId()).collect(Collectors.toSet()) +
                '}';
    }
}