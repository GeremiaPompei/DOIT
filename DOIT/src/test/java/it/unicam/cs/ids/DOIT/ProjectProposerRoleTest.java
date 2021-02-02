package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.entity.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.entity.role.RolesHandler;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
        user1 = new User("Saverio", "Tommasi", LocalDate.of(1999,9,9), "Male", "saveriotommasi@gmail.com", "password");
        user2 = new User("Giacomo", "Pier", LocalDate.of(1999,9,9), "Male", "giacomopier@gmail.com", "password");
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
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().acceptPR(pr);
        assertTrue(project.getTeam().getProgramManager().equals(rh2.getProgramManagerRole()));
    }

    @Test
    void removePR() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().removePR(pr, "si");
        assertTrue(project.getTeam().getProgramManagerRequest().isEmpty());
    }

    @Test
    void getPartecipationRequestsByProject() {
        assertTrue(rh1.getProjectProposerRole().getProjects().isEmpty());
        rh1.getProjectProposerRole().createProject("project", "description", category, a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = rh2.getProgramManagerRole().myPartecipationRequests().stream().findFirst().orElse(null);
        assertEquals(pr, rh1.getProjectProposerRole().getPartecipationRequestsByProject(project).stream().findFirst().orElse(null));
    }

    @Test
    void removeProject(){
        rh1.getProjectProposerRole().createProject("esperimento sulla relatività "," studio sperimentale del fenomeno",category,a);
        Project project = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project);
        PartecipationRequest<ProgramManagerRole> pr = project.getTeam().getProgramManagerRequest().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().removeProject(project);
        assertFalse(project.getTeam().getProgramManagerRequest().contains(pr));
        assertFalse(rh1.getProjectProposerRole().getProjects().contains(project));
        rh1.getProjectProposerRole().createProject("esperimento sulla forza di gravità"," studio sperimentale del fenomeno",category,a);
        Project project2 = rh1.getProjectProposerRole().getProjects().stream().findFirst().orElse(null);
        rh2.getProgramManagerRole().createPartecipationRequest(project2);
        PartecipationRequest<ProgramManagerRole> pr1 = project2.getTeam().getProgramManagerRequest().stream().findFirst().orElse(null);
        rh1.getProjectProposerRole().acceptPR(pr1);
        assertThrows(IllegalArgumentException.class,()->{
            rh1.getProjectProposerRole().removeProject(project2);
        });
    }
}