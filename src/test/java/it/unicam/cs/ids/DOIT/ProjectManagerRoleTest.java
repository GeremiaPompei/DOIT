package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.role.DesignerRole;
import it.unicam.cs.ids.DOIT.entity.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.entity.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.entity.role.RolesHandler;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectManagerRoleTest {

    Category category;
    User user1;
    User user2;
    User user3;
    User user4;
    RolesHandler rh1;
    RolesHandler rh2;
    RolesHandler rh3;
    RolesHandler rh4;
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
        user1 = new User("Saverio", "Tommasi", LocalDate.of(1999,9,9), "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User("Giacomo", "Pier", LocalDate.of(1999,9,9), "Male", "giacomopier@gmail.com", "password");
        user3 = new User(0L,"Daniele", "Baio", LocalDate.of(1999,9,9), "Male", "danielebaio@gmail.com", "password");
        user4 = new User(1L,"Giacomo", "Piergi", LocalDate.of(1999,9,9), "Male", "giacomopiergi@gmail.com", "password");
        rh1 = user1.getRolesHandler(user1.tokenHandlerGet().getToken());
        rh1.addRole(ProjectProposerRole.TYPE, category);
        rh2 = user2.getRolesHandler(user2.tokenHandlerGet().getToken());
        rh2.addRole(ProgramManagerRole.TYPE, category);
        rh3 = user3.getRolesHandler(user3.tokenHandlerGet().getToken());
        rh3.addRole(DesignerRole.TYPE, category);
        rh4 = user4.getRolesHandler(user4.tokenHandlerGet().getToken());
        rh4.addRole(DesignerRole.TYPE, category);
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pmPR = rh2.getProgramManagerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().acceptPR(pmPR);
        rh2.getProgramManagerRole().openRegistrations(project);
        rh3.getDesignerRole().createPartecipationRequest(project);
        rh4.getDesignerRole().createPartecipationRequest(project);
        PartecipationRequest<DesignerRole> pr1 = rh3.getDesignerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr1);
        PartecipationRequest<DesignerRole> pr2 = rh4.getDesignerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr2);
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
        assertFalse(rh2.getProgramManagerRole().getProjects().stream().findFirst().orElse(null).getTeam().isOpen());
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
        rh3.getProjectManagerRole().insertEvaluation(user4, project, 3);
        assertEquals(rh4.getDesignerRole().getEvaluations().stream().findFirst().orElse(null).getEvaluation(), 3);
    }

    @Test
    void getDesigners() {
        assertTrue(rh3.getProjectManagerRole().getDesigners(project).contains(rh4.getDesignerRole().getIdUser()));
    }

    @Test
    void exitAll() {
        assertThrows(IllegalArgumentException.class, () -> rh3.getProjectManagerRole().exitAll(project));
        rh3.getProjectManagerRole().insertEvaluation(user4, project, 3);
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

    @Test
    void  removeProject(){
        rh3.getProjectManagerRole().removeProject(user3, user4,project);
        assertTrue(rh3.getDesignerRole().getProjects().contains(project));
        assertFalse(rh3.getProjectManagerRole().getProjects().contains(project));
        assertTrue(rh4.getProjectManagerRole().getProjects().contains(project));
        assertFalse(rh4.getDesignerRole().getProjects().contains(project));
        assertNotEquals(rh3.getProjectManagerRole(), project.getTeam().getProjectManager());
        assertEquals(rh4.getProjectManagerRole(), project.getTeam().getProjectManager());
    }
}