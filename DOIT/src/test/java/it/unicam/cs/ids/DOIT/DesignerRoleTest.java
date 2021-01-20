
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

class DesignerRoleTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IUser user4;
    private IProject project1;
    private IProject project2;
    private ICategory category1;
    private ICategory category2;
    private ICategory category3;

    @BeforeEach
    void init() throws ReflectiveOperationException, RoleException {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
        category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        category2 = ServicesHandler.getInstance().getFactoryModel().createCategory("Informatica", "Descrizione.");
        category3 = ServicesHandler.getInstance().getFactoryModel().createCategory("Domotica", "Descrizione.");
        user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
        user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
        user3 = ServicesHandler.getInstance().getFactoryModel().createUser("Franca", "Suria", "1994", "Female");
        user4 = ServicesHandler.getInstance().getFactoryModel().createUser("Geremia", "Pompei", "1984", "molto");
        user2.addRole(ProjectProposerRole.class, category1.getName());
        user2.addRole(ProgramManagerRole.class, category1.getName());
        user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
        project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
        user2.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project1.getId());
        user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
        user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
        user2.getRole(ProjectProposerRole.class).createProject("SO", "sistema operativo", category2.getName());
        project2 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                .stream().filter(x -> x.getName() == "SO").findFirst().orElse(null);
        user1.addRole(DesignerRole.class, category1.getName());
        user1.addRole(ProjectManagerRole.class, category1.getName());
        user3.addRole(DesignerRole.class, category2.getName());
        user4.addRole(DesignerRole.class, category1.getName());
    }

    @Test
    void testCreatePartecipationRequest() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            assertNotNull(category1);
            assertThrows(IllegalArgumentException.class,
                    () -> user3.getRole(DesignerRole.class).createPartecipationRequest(project1.getId()));
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
            assertThrows(IllegalArgumentException.class, () -> user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId()));
            assertTrue(user1.getRole(DesignerRole.class).getPartecipationRequests().contains(pr));
            assertTrue(project1.getTeam().getDesignerRequest().contains(pr));
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertThrows(IllegalArgumentException.class,
                    () -> user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProjects() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            assertTrue(user1.getRole(DesignerRole.class).getProjects(category1.getName()).contains(project1));
            assertFalse(user1.getRole(DesignerRole.class).getProjects(category1.getName()).contains(project2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEnterEvaluation() {
        try {
            user2.getRole(ProgramManagerRole.class).openRegistrations(project1.getId());
            user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            user4.getRole(DesignerRole.class).createPartecipationRequest(project1.getId());
            user2.getRole(ProgramManagerRole.class).acceptPR(user1.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).acceptPR(user4.getId(), project1.getId());
            user2.getRole(ProgramManagerRole.class).setProjectManager(user1.getId(), project1.getId());
            user1.getRole(ProjectManagerRole.class).insertEvaluation(user4.getId(), project1.getId(), 4);
            assertEquals(user4.getRole(DesignerRole.class).getEvaluations().get(project1.getTeam()), 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
