/*

package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.ITeam;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.role.RoleException;
import it.unicam.cs.ids.DOIT.role.Team;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private IUser user1;
    ICategory category1;
    IProject project;

    @BeforeEach
    void init() {
        try {
            ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            user1.addRole(ProjectProposerRole.class, category1.getName());
            user1.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            project = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateProject() {
        try {
            assertEquals(project, ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProjectState() {
        assertEquals(project.getProjectState().getId(), 0);
        assertNotEquals(project.getProjectState().getId(), 1);
    }

    @Test
    void setProjectState() {
        assertEquals(project.getProjectState().getId(), 0);
        project.setProjectState(ServicesHandler.getInstance().getResourceHandler().getProjectState(1));
        assertEquals(project.getProjectState().getId(), 1);
    }

    @Test
    void setTeam() {
        try {
            assertNull(project.getTeam());
            ITeam team = new Team(project, user1.getRole(ProjectProposerRole.class));
            project.setTeam(team);
            assertEquals(project.getTeam(), team);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTeam() {
        try {
            assertNull(project.getTeam());
            ITeam team = new Team(project, user1.getRole(ProjectProposerRole.class));
            project.setTeam(team);
            assertEquals(project.getTeam(), team);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getId() {
        assertEquals(project.getId(), ServicesHandler.getInstance().getResourceHandler().getProject(project.getId()).getId());
    }

    @Test
    void getName() {
        assertEquals(project.getName(), ServicesHandler.getInstance().getResourceHandler().getProject(project.getId()).getName());
    }

    @Test
    void getDescription() {
        assertEquals(project.getDescription(), ServicesHandler.getInstance().getResourceHandler().getProject(project.getId()).getDescription());

    }

    @Test
    void getCategory() {
        assertEquals(project.getCategory(), ServicesHandler.getInstance().getResourceHandler().getProject(project.getId()).getCategory());

    }
}
*/
