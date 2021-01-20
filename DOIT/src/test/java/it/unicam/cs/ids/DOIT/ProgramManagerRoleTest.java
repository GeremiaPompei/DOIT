
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramManagerRoleTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IUser user4;
    private IProject project1;
    private IProject project2;
    private ICategory category1;
    private ICategory category2;

    @BeforeEach
    void init() {
        try {
            ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            category2 = ServicesHandler.getInstance().getFactoryModel().createCategory("Informatica", "Descrizione.");
            user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            user3 = ServicesHandler.getInstance().getFactoryModel().createUser("Franca", "Suria", "1994", "Female");
            user4 = ServicesHandler.getInstance().getFactoryModel().createUser("Sara", "Giampitelli", "2000", "Female");
            user2.addRole(ProjectProposerRole.class, category1.getName());
            user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            user2.getRole(ProjectProposerRole.class).createProject("SO", "sistema operativo", category2.getName());
            project2 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "SO").findFirst().orElse(null);
            user2.addRole(ProgramManagerRole.class, category1.getName());
            user2.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project1.getId());
            user4.addRole(ProgramManagerRole.class, category2.getName());
            user2.getRole(ProjectProposerRole.class).createTeam(user4.getId(), project2.getId());
            user1.addRole(DesignerRole.class, category1.getName());
            user3.addRole(DesignerRole.class, category2.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAcceptPR() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
                    }, "Il Program Manager non possiede il team: [1]");
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), "Congratulations! You are accepted.");
            assertFalse(project1.getTeam().getDesignerRequest().contains(pr));
            assertTrue(project1.getTeam().getDesigners().contains(user1.getRole(DesignerRole.class)));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemovePartecipationRequest() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).removePR(user1.getId(), project1.getId(), "Mi dispiace");
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removePR(user1.getId(), project1.getId(), "");
                    }, "La descrizione non può essere vuota!");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removePR(user1.getId(), project1.getId(), null);
                    }, "La descrizione non può essere vuota!");
            user2.getRole(ProgramManagerRole.class).removePR(user1.getId(), project1.getId(), "Mi dispiace...");
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), "Mi dispiace...");
            assertFalse(project1.getTeam().getDesignerRequest().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDesigners() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertThrows(IllegalArgumentException.class, () -> user2.getRole(ProgramManagerRole.class).getDesigners(project2.getId()));
            assertTrue(user2.getRole(ProgramManagerRole.class).getDesigners(project1.getId()).contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveDesigner() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertTrue(project1.getTeam().getDesigners().contains(user1.getRole(DesignerRole.class)));
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).removeDesigner(user1.getId(), project1.getId());
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removeDesigner(user3.getId(), project1.getId());
                    }, "Il Program Manager non è interno al team: [1]");
            user2.getRole(ProgramManagerRole.class).removeDesigner(user1.getId(), project1.getId());
            assertFalse(project1.getTeam().getDesigners().contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).setProjectManager(user1.getId(), project2.getId());
                    }, "L'utente non possiede il progetto con id:[2]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).setProjectManager(user3.getId(), project1.getId());
                    }, "L'utente non è presente nel team del progetto!");
            user2.getRole(ProgramManagerRole.class).setProjectManager(user1.getId(), project1.getId());
            assertEquals(user1.getRole(ProjectManagerRole.class), project1.getTeam().getProjectManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetPartecipationRequests() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).getPartecipationRequests(project1.getId());
                    }, "Team non posseduto: [1]");
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            assertTrue(project1.getTeam().getDesignerRequest().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testOpenRegistrations() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
                    }, "Le registrazioni non sono aperte !");
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
            assertTrue(project1.getTeam().getDesignerRequest().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCloseRegistrations() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            assertTrue(project1.getTeam().isOpen());
            user2.getRole(ProgramManagerRole.class).closeRegistrations(project1.getId());
            assertFalse(project1.getTeam().isOpen());
        } catch (RoleException e) {
            e.printStackTrace();
    }
    }
}
