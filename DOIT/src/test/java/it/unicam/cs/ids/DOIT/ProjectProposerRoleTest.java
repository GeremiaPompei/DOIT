package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.model.role.RolesHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerRoleTest {

    Category category;
    User user1;
    User user2;
    RolesHandler rh1;
    RolesHandler rh2;
    ProjectState a;
    ProjectState b;
    ProjectState c;

    @BeforeEach()
    void init() {
        a = new ProjectState((long) 0, "INIZIALIZZAZIONE", "Stato iniziale.");
        b = new ProjectState((long) 1, "SVILUPPO", "Stato di sviluppo.");
        c = new ProjectState((long) 2, "TERMINALE", "Stato terminale.");
        category = new Category("Fisica", "Descrizione");
        user1 = new User("Saverio", "Tommasi", "1998", "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User("Giacomo", "Pier", "1998", "Male", "giacomopier@gmail.com", "password");
        rh1 = user1.getRolesHandler(user1.tokenHandlerGet().getToken());
        rh1.addRole(ProjectProposerRole.TYPE, category);
        rh2 = user2.getRolesHandler(user2.tokenHandlerGet().getToken());
        rh2.addRole(ProgramManagerRole.TYPE, category);
    }

    @Test
    void getType() {
        assertEquals(rh1.getProjectProposerRole().getType(), ProjectProposerRole.TYPE);
    }

    @Test
    void createProject() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        assertFalse(rh1.getProjectProposerRole().getProjects().isEmpty());
    }

    @Test
    void acceptPR() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().acceptPR(pr);
        assertTrue(project.getTeam().getProgramManager().equals(rh2.getProgramManagerRole()));
    }

    @Test
    void removePR() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().removePR(pr, "si");
        assertTrue(project.getTeam().getProgramManagerRequest().isEmpty());
    }

    @Test
    void getPartecipationRequestsByProject() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        assertEquals(pr, rh1.getProjectProposerRole().getPartecipationRequestsByProject(project).stream().findFirst().orElse(null));
    }
}