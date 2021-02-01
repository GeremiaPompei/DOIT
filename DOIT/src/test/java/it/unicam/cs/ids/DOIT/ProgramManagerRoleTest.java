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

class ProgramManagerRoleTest {

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

    @BeforeEach()
    void init() {
        a = new ProjectState((long) 0, "INIZIALIZZAZIONE", "Stato iniziale.");
        b = new ProjectState((long) 1, "SVILUPPO", "Stato di sviluppo.");
        c = new ProjectState((long) 2, "TERMINALE", "Stato terminale.");
        category = new Category("Fisica", "Descrizione");
        user1 = new User(1L, "Saverio", "Tommasi", LocalDate.of(1999,9,9), "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User(2L, "Giacomo", "Pier", LocalDate.of(1999,9,9), "Male", "giacomopier@gmail.com", "password");
        user3 = new User(3L, "Daniele", "Baio", LocalDate.of(1999,9,9), "Male", "danielebaio@gmail.com", "password");
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
    }

    @Test
    void getType() {
        assertEquals(rh2.getProgramManagerRole().getType(), ProgramManagerRole.TYPE);
        assertNotEquals(rh2.getProgramManagerRole().getType(), ProjectProposerRole.TYPE);
    }

    @Test
    void acceptPR() {
        assertFalse(project.getTeam().getDesignerRequest().isEmpty());
        assertTrue(project.getTeam().getDesigners().isEmpty());
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        assertTrue(project.getTeam().getDesignerRequest().isEmpty());
        assertFalse(project.getTeam().getDesigners().isEmpty());
    }

    @Test
    void removePR() {
        assertFalse(project.getTeam().getDesignerRequest().isEmpty());
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        assertTrue(project.getTeam().getDesignerRequest().isEmpty());

    }

    @Test
    void getPartecipationRequestsByProject() {
        assertFalse(rh2.getProgramManagerRole().getPartecipationRequestsByProject(project).isEmpty());
    }

    @Test
    void openRegistrations() {
        User user4 = new User("Geremia", "Pom", LocalDate.of(1999,9,9), "Male", "geremiapom@gmail.com", "password");
        RolesHandler rh4 = user4.getRolesHandler(user4.tokenHandlerGet().getToken());
        rh4.addRole(DesignerRole.TYPE, category);
        project.getTeam().getDesignerRequest().removeAll(project.getTeam().getDesignerRequest());
        rh2.getProgramManagerRole().closeRegistrations(project);
        assertThrows(IllegalArgumentException.class, () -> rh4.getDesignerRole().createPartecipationRequest(project));
        rh2.getProgramManagerRole().openRegistrations(project);
        assertDoesNotThrow(() -> rh4.getDesignerRole().createPartecipationRequest(project));
        PartecipationRequest<DesignerRole> pr = rh4.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        assertTrue(project.getTeam().getDesignerRequest().contains(pr));
    }

    @Test
    void closeRegistrations() {
        User user4 = new User("Geremia", "Pom", LocalDate.of(1999,9,9), "Male", "geremiapom@gmail.com", "password");
        RolesHandler rh4 = user4.getRolesHandler(user4.tokenHandlerGet().getToken());
        rh4.addRole(DesignerRole.TYPE, category);
        project.getTeam().getDesignerRequest().removeAll(project.getTeam().getDesignerRequest());
        rh4.getDesignerRole().createPartecipationRequest(project);
        rh2.getProgramManagerRole().closeRegistrations(project);
        assertThrows(IllegalArgumentException.class, () -> rh4.getDesignerRole().createPartecipationRequest(project));
    }

    @Test
    void getProjectsByCategory() {
        List<Project> list = new ArrayList<>();
        list.add(project);
        assertFalse(rh2.getProgramManagerRole().getProjectsByCategory(list.iterator(), category).contains(project));
    }

    @Test
    void getMyPartecipationRequests() {
        assertNotNull(rh2.getProgramManagerRole().getMyPartecipationRequests());
    }

    @Test
    void getIdDesigners() {
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        assertEquals(rh2.getProgramManagerRole().getIdDesigners(project).stream().findFirst().orElse(null), 3);
    }

    @Test
    void removeDesigner() {
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        assertTrue(rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null).getTeam().getDesigners().contains(rh3.getDesignerRole()));
        rh2.getProgramManagerRole().removeDesigner(user3, project);
        assertFalse(rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null).getTeam().getDesigners().contains(rh3.getDesignerRole()));

    }

    @Test
    void setProjectManager() {
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().acceptPR(pr);
        assertTrue(rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null).getTeam().getDesigners().contains(rh3.getDesignerRole()));
        rh2.getProgramManagerRole().setProjectManager(user3, project);
        assertFalse(rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null).getTeam().getDesigners().contains(rh3.getDesignerRole()));
    }
}