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

class FactoryModelTest {

    @BeforeEach
    void init() {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
    }

    @Test
    void createProject() {
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
        ICategory category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        ServicesHandler.getInstance().getFactoryModel().createProject("Campo da calcio", "Calcio a 5", category1);
        IProject project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
        assertEquals(project1.getDescription(), "Calcio a 5");
    }

    @Test
    void createCategory() {
        ICategory category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        assertEquals(category1, ServicesHandler.getInstance().getResourceHandler().getCategory(category1.getName()));

    }

    @Test
    void createProjectState() {
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        assertEquals("INIZIALIZZAZIONE", ServicesHandler.getInstance().getResourceHandler().getProjectState(0).getName());
    }

    @Test
    void createUser() {
        IUser user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
        assertEquals(user1, ServicesHandler.getInstance().getResourceHandler().getUser(user1.getId()));
    }

    @Test
    void createPartecipationRequest() {
        try {
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            ICategory category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            IUser user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            IUser user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            user2.addRole(ProjectProposerRole.class, category1.getName());
            user2.addRole(ProgramManagerRole.class, category1.getName());
            user1.addRole(DesignerRole.class, category1.getName());
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            IProject project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            user2.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project1.getId());
            IPartecipationRequest pr = ServicesHandler.getInstance().getFactoryModel().createPartecipationRequest(user1.getRole(DesignerRole.class), project1.getTeam());
            assertEquals(pr.getTeam(), project1.getTeam());
        } catch (RoleException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTeam() {
        try {
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            ICategory category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            IUser user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            IUser user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            user2.addRole(ProjectProposerRole.class, category1.getName());
            user2.addRole(ProgramManagerRole.class, category1.getName());
            user1.addRole(DesignerRole.class, category1.getName());
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            IProject project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            ITeam team = ServicesHandler.getInstance().getFactoryModel().createTeam(project1, user2.getRole(ProjectProposerRole.class));
            assertEquals(team.getProject(), project1);
        } catch (RoleException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createRole() {
        try {
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            ICategory category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            IUser user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            IUser user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            ProjectProposerRole ppr = ServicesHandler.getInstance().getFactoryModel().createRole(ProjectProposerRole.class, user1, category1);
            ProgramManagerRole pmr = ServicesHandler.getInstance().getFactoryModel().createRole(ProgramManagerRole.class, user2, category1);
            assertNotNull(ppr);
            assertNotNull(pmr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/
