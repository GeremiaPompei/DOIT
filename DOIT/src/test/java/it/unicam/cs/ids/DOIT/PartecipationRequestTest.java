/*
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartecipationRequestTest {

    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IProject project1;
    private ICategory category1;
    private ICategory category2;
    IPartecipationRequest pr;

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
            user2.addRole(ProgramManagerRole.class, category1.getName());
            user2.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project1.getId());
            user1.addRole(DesignerRole.class, category1.getName());
            user1.addRole(ProjectManagerRole.class, category1.getName());
            user3.addRole(DesignerRole.class, category1.getName());
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            pr = ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId())
                    .getTeam().getDesignerRequest().stream()
                    .filter(d -> {
                        try {
                            return d.getPendingRole() == user1.getRole(DesignerRole.class);
                        } catch (RoleException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPendingRole() {
        try {
            assertNotEquals(pr.getPendingRole(), user1.getRole(ProjectManagerRole.class));
            assertEquals(pr.getPendingRole(), user1.getRole(DesignerRole.class));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void displayed() {
        assertFalse(pr.getState());
        pr.displayed("ok");
        assertTrue(pr.getState());
        assertEquals(pr.getDescription(), "ok");
    }

    @Test
    void getTeam() {
        try {
            assertEquals(pr.getTeam(), project1.getTeam());
            project1.getTeam().addDesigner(user3.getRole(DesignerRole.class));
            assertEquals(pr.getTeam(), project1.getTeam());
        } catch (RoleException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getDescription() {
        assertEquals(pr.getDescription(), "Partecipation request sent...");
        pr.displayed("ok");
        assertEquals(pr.getDescription(), "ok");
    }

    @Test
    void getState() {
        assertFalse(pr.getState());
        pr.displayed("ok");
        assertTrue(pr.getState());
    }
}*/
