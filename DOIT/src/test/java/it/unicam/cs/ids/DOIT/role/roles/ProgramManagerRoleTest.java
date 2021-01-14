package it.unicam.cs.ids.DOIT.role.roles;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.FactoryModel;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.service.ResourceHandler;
import it.unicam.cs.ids.DOIT.service.IResourceHandler;
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
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;

    @BeforeEach
    void init() {
        try {
            resourceHandler = new ResourceHandler();
            resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
            factory = new FactoryModel(resourceHandler);
            factory.createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            factory.createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            factory.createProjectState(2, "TERMINALE", "Stato terminale.");
            factory.createCategory("Sport", "Descrizione.");
            factory.createCategory("Informatica", "Descrizione.");
            factory.createCategory("Domotica", "Descrizione.");
            user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = factory.createUser(3, "Franca", "Suria", 1994, "Female");
            user4 = factory.createUser(4, "Sara", "Giampitelli", 2000, "Female");
            category1 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("SPORT"));
            category2 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("INFORMATICA"));
            user2.addRole(ProjectProposerRole.class, category1, factory);
            user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
            project1 = user2.getRole(ProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
            project2 = user2.getRole(ProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2, factory);
            user2.addRole(ProgramManagerRole.class, category1, factory);
            user2.getRole(ProjectProposerRole.class).createTeam(user2, project1, factory);
            user4.addRole(ProgramManagerRole.class, category2, factory);
            user2.getRole(ProjectProposerRole.class).createTeam(user4, project2, factory);
            user1.addRole(DesignerRole.class, category1, factory);
            user3.addRole(DesignerRole.class, category2, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        try {
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            user2.getRole(IProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProgramManagerRole.class).setProjectManager(user1, project2, ProgramManagerRole.class);
                    }, "L'utente non possiede il progetto con id:[2]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProgramManagerRole.class).setProjectManager(user3, project1, ProgramManagerRole.class);
                    }, "L'utente non è presente nel team del progetto!");
            user2.getRole(IProgramManagerRole.class).setProjectManager(user1, project1, ProgramManagerRole.class);
            assertEquals(user1, project1.getProjectManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTeam() {
        try {
            assertTrue(user4.getRole(IProgramManagerRole.class).getTeams().contains(project2.getTeam()));
            assertTrue(user4.getRole(IProgramManagerRole.class).getProjects().contains(project2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemovePartecipationRequest() {
        try {
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, "Mi dispiace");
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, "");
                    }, "La descrizione non può essere vuota!");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, null);
                    }, "La descrizione non può essere vuota!");
            user2.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, "Mi dispiace...");
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), "Mi dispiace...");
            assertFalse(project1.getTeam().getPartecipationRequests().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveDesigner() {
        try {
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            user2.getRole(IProgramManagerRole.class).addDesigner(pr);
            assertTrue(project1.getTeam().getDesigners().contains(user1));
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(IProgramManagerRole.class).removeDesigner(user1, project1.getTeam());
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProgramManagerRole.class).removeDesigner(user3, project1.getTeam());
                    }, "Il Program Manager non è interno al team: [1]");
            user2.getRole(IProgramManagerRole.class).removeDesigner(user1, project1.getTeam());
            assertFalse(project1.getTeam().getDesigners().contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDesigner() {
        try {
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(IProgramManagerRole.class).addDesigner(pr);
                    }, "Il Program Manager non possiede il team: [1]");
            user2.getRole(IProgramManagerRole.class).addDesigner(pr);
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), "Congratulations! You are accepted.");
            assertFalse(project1.getTeam().getPartecipationRequests().contains(pr));
            assertTrue(project1.getTeam().getDesigners().contains(user1));

        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDesigners() {
        try {
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            user2.getRole(IProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class, () -> user2.getRole(IProgramManagerRole.class).getDesigners(project2.getTeam()));
            assertTrue(user2.getRole(IProgramManagerRole.class).getDesigners(project1.getTeam()).contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetPartecipationRequests() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(IProgramManagerRole.class).getPartecipationRequests(project1.getTeam());
                    }, "Team non posseduto: [1]");
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            assertTrue(project1.getTeam().getPartecipationRequests().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testGetTeam() {
        try {
            assertEquals(project1.getTeam(), user2.getRole(IProgramManagerRole.class).getTeams().stream().findFirst().get());
            assertNotEquals(project2.getTeam(), user2.getRole(IProgramManagerRole.class).getTeams().stream().findFirst().get());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }


}