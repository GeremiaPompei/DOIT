package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramManagerRoleTest {
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Project project1;
    private Project project2;
    private Category category1;
    private Category category2;

    @BeforeEach
    void init() {
        try {
            GestoreRisorse.getInstance().clear();
            new Category("Sport", "Descrizione.");
            new Category("Informatica", "Descrizione.");
            new Category("Domotica", "Descrizione.");
            user1 = new User(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = new User(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = new User(3, "Franca", "Suria", 1994, "Female");
            user4 = new User(4, "Sara", "Giampitelli", 2000, "Female");
            category1 = GestoreRisorse.getInstance().searchOne(Category.class, x -> x.getName().equals("SPORT"));
            category2 = GestoreRisorse.getInstance().searchOne(Category.class, x -> x.getName().equals("INFORMATICA"));
            user2.addRole(ProjectProposerRole.class, category1);
            user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
            project1 = user2.getRole(ProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1);
            project2 = user2.getRole(ProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2);
            user2.addRole(ProgramManagerRole.class, category1);
            user2.getRole(ProjectProposerRole.class).createTeam(user2, project1);
            user4.addRole(ProgramManagerRole.class, category2);
            user2.getRole(ProjectProposerRole.class).createTeam(user4, project2);
            user1.addRole(DesignerRole.class, category1);
            user3.addRole(DesignerRole.class, category2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        try {
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            user2.getRole(ProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).setProjectManager(user1, project2);
                    }, "L'utente non possiede il progetto con id:[2]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).setProjectManager(user3, project1);
                    }, "L'utente non è presente nel team del progetto!");
            user2.getRole(ProgramManagerRole.class).setProjectManager(user1, project1);
            assertEquals(user1, project1.getProjectManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTeam() {
        try {
            assertTrue(user4.getRole(ProgramManagerRole.class).getTeams().contains(project2.getTeam()));
            assertTrue(user4.getRole(ProgramManagerRole.class).getProjects().contains(project2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemovePartecipationRequest() {
        try {
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, "Mi dispiace");
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, "");
                    }, "La descrizione non può essere vuota!");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, null);
                    }, "La descrizione non può essere vuota!");
            user2.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, "Mi dispiace...");
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
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            user2.getRole(ProgramManagerRole.class).addDesigner(pr);
            assertTrue(project1.getTeam().getDesigners().contains(user1));
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).removeDesigner(user1, project1.getTeam());
                    }, "Il Program Manager non possiede il team: [1]");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProgramManagerRole.class).removeDesigner(user3, project1.getTeam());
                    }, "Il Program Manager non è interno al team: [1]");
            user2.getRole(ProgramManagerRole.class).removeDesigner(user1, project1.getTeam());
            assertFalse(project1.getTeam().getDesigners().contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDesigner() {
        try {
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).addDesigner(pr);
                    }, "Il Program Manager non possiede il team: [1]");
            user2.getRole(ProgramManagerRole.class).addDesigner(pr);
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
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            user2.getRole(ProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class, () -> user2.getRole(ProgramManagerRole.class).getDesigners(project2.getTeam()));
            assertTrue(user2.getRole(ProgramManagerRole.class).getDesigners(project1.getTeam()).contains(user1));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetPartecipationRequests() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user4.getRole(ProgramManagerRole.class).getPartecipationRequests(project1.getTeam());
                    }, "Team non posseduto: [1]");
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            assertTrue(project1.getTeam().getPartecipationRequests().contains(pr));
        } catch (RoleException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testGetTeam() {
        try {
            assertEquals(project1.getTeam(), user2.getRole(ProgramManagerRole.class).getTeams().stream().findFirst().get());
            assertNotEquals(project2.getTeam(), user2.getRole(ProgramManagerRole.class).getTeams().stream().findFirst().get());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }


}