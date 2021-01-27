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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectManagerRoleTest {

    Category category;
    User user1;
    User user2;
    User user3;
    RolesHandler rh1;
    RolesHandler rh2;
    RolesHandler rh3;
    ProjectState a;
    ProjectState b;
    ProjectState c;
    Project project;
    List<ProjectState> states;

    @BeforeEach()
    void init() {
        a = new ProjectState((long) 0, "INIZIALIZZAZIONE", "Stato iniziale.");
        b = new ProjectState((long) 1, "SVILUPPO", "Stato di sviluppo.");
        c = new ProjectState((long) 2, "TERMINALE", "Stato terminale.");
        states = new ArrayList<>();
        states.add(a);
        states.add(b);
        states.add(c);
        category = new Category("Fisica", "Descrizione");
        user1 = new User("Saverio", "Tommasi", "1998", "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User("Giacomo", "Pier", "1998", "Male", "giacomopier@gmail.com", "password");
        user3 = new User(0L,"Daniele", "Baio", "1998", "Male", "danielebaio@gmail.com", "password");
        rh1 = user1.getRolesHandler(user1.tokenHandlerGet().getToken());
        rh1.addRole(ProjectProposerRole.TYPE, category);
        rh2 = user2.getRolesHandler(user2.tokenHandlerGet().getToken());
        rh2.addRole(ProgramManagerRole.TYPE, category);
        rh3 = user3.getRolesHandler(user3.tokenHandlerGet().getToken());
        rh3.addRole(DesignerRole.TYPE, category);
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pmPR = rh2.getProgramManagerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().acceptPR(pmPR);
        rh2.getProgramManagerRole().openRegistrations(project);
        rh3.getDesignerRole().createPartecipationRequest(project);
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        rh2.getProgramManagerRole().setProjectManager(user3, project);
    }

    @Test
    void getType() {
        assertEquals(rh2.getProgramManagerRole().getType(), ProgramManagerRole.TYPE);
        assertNotEquals(rh2.getProgramManagerRole().getType(), ProjectProposerRole.TYPE);
    }

    @Test
    void upgradeState() {
        assertEquals(project.getProjectState().getId(), 0);
        rh3.getProjectManagerRole().upgradeState(states.iterator(), project);
        assertEquals(project.getProjectState().getId(), 1);
        rh3.getProjectManagerRole().upgradeState(states.iterator(), project);
        assertThrows(IllegalArgumentException.class, () -> rh3.getProjectManagerRole().upgradeState(states.iterator(), project));
    }

    @Test
    void downgradeState() {
        assertThrows(IllegalArgumentException.class, () -> rh3.getProjectManagerRole().downgradeState(states.iterator(), project));
        assertEquals(project.getProjectState().getId(), 0);
        rh3.getProjectManagerRole().upgradeState(states.iterator(), project);
        assertEquals(project.getProjectState().getId(), 1);
        rh3.getProjectManagerRole().downgradeState(states.iterator(), project);
        assertEquals(project.getProjectState().getId(), 0);
    }

    @Test
    void insertEvaluation() {
        rh3.getProjectManagerRole().insertEvaluation(user3, project, 3);
        assertEquals(rh3.getDesignerRole().getEvaluations().stream().findFirst().orElse(null).getEvaluate(), 3);
    }

    @Test
    void getDesigners() {
        assertTrue(rh3.getProjectManagerRole().getDesigners(project).contains(rh3.getDesignerRole().getIdUser()));
    }

    @Test
    void exitAll() {
        assertThrows(IllegalArgumentException.class, () -> rh3.getProjectManagerRole().exitAll(project));
        rh3.getProjectManagerRole().insertEvaluation(user3, project, 3);
        assertDoesNotThrow(() -> rh3.getProjectManagerRole().exitAll(project));
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        assertTrue(rh2.getProgramManagerRole().getProjects().isEmpty());
        assertTrue(rh3.getDesignerRole().getProjects().isEmpty());
    }

    @Test
    void getProjectState() {
        assertEquals(rh3.getProjectManagerRole().getProjectState(project).getId(), 0);
        rh3.getProjectManagerRole().upgradeState(states.iterator(), project);
        assertEquals(rh3.getProjectManagerRole().getProjectState(project).getId(), 1);
    }
}