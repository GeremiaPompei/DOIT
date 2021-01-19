/*
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectManagerRoleTest {

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
            user1.addRole(DesignerRole.class, category1.getName());
            user1.addRole(ProjectManagerRole.class, category1.getName());
            user3.addRole(DesignerRole.class, category1.getName());
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            user3.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).acceptPR(user3.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).setProjectManager(user1.getId(), project1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void upgradeState() {
        try {
            assertEquals(project1.getProjectState().getName(), "INIZIALIZZAZIONE");
            user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "SVILUPPO");
            user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "TERMINALE");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
                    }, "Stato progetto terminale, non si può procedere oltre!");
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void downgradeState() {
        try {
            user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
            user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "TERMINALE");
            user1.getRole(ProjectManagerRole.class).downgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "SVILUPPO");
            user1.getRole(ProjectManagerRole.class).downgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "INIZIALIZZAZIONE");
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user1.getRole(ProjectManagerRole.class).downgradeState(project1.getId());
                    }, "Stato progetto iniziale, non si può procedere oltre!");
        } catch (RoleException e) {
            e.printStackTrace();
        }

    }

    @Test
    void insertEvaluation() {
        try {
            user1.getRole(ProjectManagerRole.class).insertEvaluation(user3.getId(), project1.getId(), 4);
            assertEquals(project1.getTeam().getDesigners().stream().filter(d -> d.getUser() == user3)
                    .findFirst().orElse(null).getEvaluations().get(project1.getTeam()), 4);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDesigners() {
        try {
            assertTrue(user1.getRole(ProjectManagerRole.class).getDesigners(project1.getId()).contains(user1));
            assertTrue(user1.getRole(ProjectManagerRole.class).getDesigners(project1.getId()).contains(user3));
            assertFalse(user1.getRole(ProjectManagerRole.class).getDesigners(project1.getId()).contains(user2));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void exitAll() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user1.getRole(ProjectManagerRole.class).exitAll(project1.getId());
                    }, "Prima di chiudere il progetto finisci di valutare i designer!");
            user1.getRole(ProjectManagerRole.class).insertEvaluation(user1.getId(), project1.getId(), 4);
            user1.getRole(ProjectManagerRole.class).insertEvaluation(user3.getId(), project1.getId(), 4);
            user1.getRole(ProjectManagerRole.class).exitAll(project1.getId());
            assertTrue(user1.getRole(ProjectManagerRole.class).getTeams().isEmpty());
            assertTrue(user1.getRole(DesignerRole.class).getTeams().isEmpty());
            assertTrue(user2.getRole(ProgramManagerRole.class).getTeams().isEmpty());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProjectState() {
        try {
            assertEquals(project1.getProjectState().getName(), "INIZIALIZZAZIONE");
            assertNotEquals(project1.getProjectState().getName(), "SVILUPPO");
            user1.getRole(ProjectManagerRole.class).upgradeState(project1.getId());
            assertEquals(project1.getProjectState().getName(), "SVILUPPO");
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}*/
