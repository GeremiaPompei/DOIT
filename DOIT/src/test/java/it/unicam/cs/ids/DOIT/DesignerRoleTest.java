package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.role.*;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DesignerRoleTest {
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
        user1 = new User("Saverio", "Tommasi", "1998", "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User("Giacomo", "Pier", "1998", "Male", "giacomopier@gmail.com", "password");
        user3 = new User("Daniele", "Baio", "1998", "Male", "danielebaio@gmail.com", "password");
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
    }

    @Test
    void testCreatePartecipationRequest() {
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().createPartecipationRequest(project);
        assertEquals(rh2.getProgramManagerRole().getPartecipationRequestsByProject(project).stream().findFirst().get(), pr);
        assertTrue(project.getTeam().getDesignerRequest().contains(pr));
    }

    @Test
    void testGetProjectsByCategory() {
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        Project p2 = rh3.getDesignerRole().getProjectsByCategory(projects.iterator(), category).stream().findFirst().get();
        assertEquals(p2, project);
    }

    @Test
    void getType() {
        assertEquals(ProgramManagerRole.TYPE, rh2.getProgramManagerRole().getType());
    }

    @Test
    void getMyPartecipationRequests() {
        PartecipationRequest<DesignerRole> pr = rh3.getDesignerRole().createPartecipationRequest(project);
        assertEquals(rh3.getDesignerRole().getMyPartecipationRequests().stream().findFirst().get(), pr);
    }

    @Test
    public void getEvaluations() {
        assertTrue(rh3.getDesignerRole().getEvaluations().isEmpty());
    }

    @Test
    public void getCurriculumVitae() {
        assertTrue(rh3.getDesignerRole().getCurriculumVitae().isEmpty());
    }
}
