package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;

import java.util.Set;

public interface ITeam {
    Long getId();

    boolean isOpen();

    ProjectProposerRole getProjectProposer();

    ProgramManagerRole getProgramManager();

    void setProgramManager(ProgramManagerRole programManagerRole);

    void addDesigner(DesignerRole designer);

    void removeDesigner(DesignerRole designer);

    ProjectManagerRole getProjectManager();

    void setProjectManager(ProjectManagerRole projectManager);

    Set<DesignerRole> getDesigners();

    Set<IPartecipationRequest> getDesignerRequest();

    Set<IPartecipationRequest> getProgramManagerRequest();

    void openRegistrations();

    void closeRegistrations();

    IProject getProject();

}
