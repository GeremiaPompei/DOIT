package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Arrays;

@Entity
public class ProjectEntity implements ResourceEntity<IProject> {
    @Transient
    @Autowired
    private ServicesHandler servicesHandler;
    @Id
    private Long id;
    private String name;
    private String description;
    private Long projectState;
    private String category;
    private boolean teamIsOpen;
    private Long teamProjectProposer;
    private Long teamProgramManager;
    private Long teamProjectManager;
    private String teamDesigners;
    private String teamDesignerRequest;
    private String teamProgramManagerRequest;

    @Override
    public void fromObject(IProject project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.projectState = project.getProjectState().getId();
        this.category = project.getCategory().getName();
        this.teamIsOpen = project.getTeam().isOpen();
        this.teamProjectProposer = project.getTeam().getProjectProposer().getUser().getId();
        this.teamProgramManager = project.getTeam().getProgramManager().getUser().getId();
        this.teamProjectManager = project.getTeam().getProjectManager().getUser().getId();
        this.teamDesigners = project.getTeam().getDesigners()
                .stream()
                .map(d -> d.getUser().getId().toString())
                .reduce((x, y) -> x + " " + y)
                .get();
        this.teamDesignerRequest = project.getTeam().getDesignerRequest()
                .stream()
                .map(d -> d.getPendingRole().getUser().getId().toString())
                .reduce((x, y) -> x + " " + y)
                .get();
        this.teamProgramManagerRequest = project.getTeam().getProgramManagerRequest()
                .stream()
                .map(d -> d.getPendingRole().getUser().getId().toString())
                .reduce((x, y) -> x + " " + y)
                .get();
    }

    public IProject toObject() throws RoleException {
        IProject project = new Project(this.id, this.name, this.description,
                servicesHandler.getResourceHandler().getCategory(this.category));
        ITeam team = servicesHandler.getFactoryModel()
                .createTeam(project, servicesHandler.getResourceHandler()
                        .getUser(this.teamProjectProposer).getRole(ProjectProposerRole.class));
        team.setProgramManager(servicesHandler.getResourceHandler()
                .getUser(this.teamProgramManager).getRole(ProgramManagerRole.class));
        team.setProjectManager(servicesHandler.getResourceHandler()
                .getUser(this.teamProjectManager).getRole(ProjectManagerRole.class));
        Arrays.stream(this.teamDesigners.split(" ")).forEach(d ->
        {
            try {
                team.addDesigner(servicesHandler.getResourceHandler().getUser(Long.parseLong(d)).getRole(DesignerRole.class));
            } catch (RoleException e) {
            }
        });
        Arrays.stream(this.teamDesignerRequest.split(" ")).forEach(d ->
                team.getDesignerRequest().add(servicesHandler.getResourceHandler().getPartecipationRequest(Long.parseLong(d), this.id, DesignerRole.class)));
        Arrays.stream(this.teamProgramManagerRequest.split(" ")).forEach(d ->
                team.getProgramManagerRequest().add(servicesHandler.getResourceHandler().getPartecipationRequest(Long.parseLong(d), this.id, ProgramManagerRole.class)));
        return project;
    }
}
