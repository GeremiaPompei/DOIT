/*

package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.ICategory;
import it.unicam.cs.ids.DOIT.model.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.IProject;
import it.unicam.cs.ids.DOIT.model.role.ITeam;
import it.unicam.cs.ids.DOIT.model.role.*;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.model.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IProject project1;
    private ICategory category1;
    private IPartecipationRequest partecipationRequest;

    @BeforeEach
    void init() {
        try {
            ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
            category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            user3 = ServicesHandler.getInstance().getFactoryModel().createUser("Sio", "Sandrio", "2000", "Male");
            user1.addRole(ProjectProposerRole.class, category1.getName());
            user1.getRole(ProjectProposerRole.class).createProject("Progetto1", "Campo da Beach", category1.getName());
            project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Progetto1").findFirst().orElse(null);
            ;
            user2.addRole(ProgramManagerRole.class, category1.getName());
            user1.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user3.addRole(DesignerRole.class, category1.getName());
            user3.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            partecipationRequest = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user3.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            user3.addRole(ProjectManagerRole.class, category1.getName());
        } catch (ReflectiveOperationException | RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetState() {
        try {
            assertTrue(project1.getTeam().isOpen());
            user2.getRole(ProgramManagerRole.class).closeRegistrations(project1.getId());
            assertFalse(project1.getTeam().isOpen());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProgramManager() {
        try {
            user3.addRole(ProgramManagerRole.class, category1.getName());
            assertNotEquals(project1.getTeam().getProgramManager(), user3.getRole(ProgramManagerRole.class));
            assertEquals(project1.getTeam().getProgramManager(), user2.getRole(ProgramManagerRole.class));
        } catch (RoleException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDesigner() {
        try {
            user2.getRole(ProgramManagerRole.class).acceptPR(user3.getId(), project1.getId());
            assertFalse(project1.getTeam().getDesignerRequest().contains(partecipationRequest));
            assertTrue(project1.getTeam().getDesigners().contains(user3.getRole(DesignerRole.class)));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveDesigner() {
        try {
            ITeam team = user2.getRole(ProgramManagerRole.class).getTeams().stream().findFirst().get();
            team.removeDesigner(user3.getRole(DesignerRole.class));
            assertFalse(project1.getTeam().getDesigners().contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDesigner() {
        Set<DesignerRole> designerSet = ServicesHandler.getInstance().getResourceHandler()
                .getProject(project1.getId()).getTeam().getDesigners();
        assertEquals(project1.getTeam().getDesigners(), designerSet);
    }

    @Test
    void testGetDesignerRequest() {
        assertTrue(project1.getTeam().getDesignerRequest().contains(partecipationRequest));
    }

    @Test
    void testSetProjectManager() {
        try {
            assertNull(project1.getTeam().getProjectManager());
            user2.getRole(ProgramManagerRole.class).acceptPR(user3.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).setProjectManager(user3.getId(), project1.getId());
            assertEquals(project1.getTeam().getProjectManager(), user3.getRole(ProjectManagerRole.class));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProjectProposer() {
        try {
            assertEquals(project1.getTeam().getProjectProposer(), user1.getRole(ProjectProposerRole.class));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TestGetProjectManager() {
        try {
            user2.getRole(ProgramManagerRole.class).acceptPR(user3.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).setProjectManager(user3.getId(), project1.getId());
            assertEquals(project1.getTeam().getProjectManager(), user3.getRole(ProjectManagerRole.class));
        } catch (RoleException e) {
            e.printStackTrace();
        } catch(ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void openRegistrations() {
        try {
            user2.getRole(ProgramManagerRole.class).closeRegistrations(project1.getId());
            assertFalse(project1.getTeam().isOpen());
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            assertTrue(project1.getTeam().isOpen());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void closeRegistrations() {
        try {
            assertTrue(project1.getTeam().isOpen());
            user2.getRole(ProgramManagerRole.class).closeRegistrations(project1.getId());
            assertFalse(project1.getTeam().isOpen());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

}
*/
