package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;

import java.util.Set;

public interface ITeam {
    int getId();

    boolean getState();

    IRole getProjectProposer();

    ProgramManagerRole getProgramManager();

    void setProgramManager(ProgramManagerRole programManagerRole);

    void addDesigner(DesignerRole designer);

    void removeDesigner(DesignerRole designer);

    IRole getProjectManager();

    void setProjectManager(ProjectManagerRole projectManager);

    Set<DesignerRole> getDesigners();

    Set<IPartecipationRequest> getDesignerRequest();

    void openRegistrations();

    void closeRegistrations();

    boolean isState();

    IProject getProject();

}
