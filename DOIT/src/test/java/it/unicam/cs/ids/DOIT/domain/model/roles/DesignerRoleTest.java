package it.unicam.cs.ids.DOIT.domain.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.simple.model.roles.DesignerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.simple.model.Category;
import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesignerRoleTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IProject project1;
    private IProject project2;
    private ICategory category1;
    private ICategory category2;
    private IResourceHandler resourceHandler;
    private IFactoryModel factory;

    @BeforeEach
    void init() throws ReflectiveOperationException, RoleException {
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
        category1 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("SPORT"));
        category2 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("INFORMATICA"));
        user2.addRole(ProjectProposerRole.class, category1, factory);
        project1 = user2.getRole(ProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
        user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
        project2 = user2.getRole(ProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2, factory);
        user2.addRole(ProgramManagerRole.class, category1, factory);
        user2.getRole(ProjectProposerRole.class).createTeam(user2, project1, factory);
        user1.addRole(DesignerRole.class, category1, factory);
        user3.addRole(DesignerRole.class, category2, factory);
    }

    @Test
    void testCreatePartecipationRequest() {
        try {
            assertNotNull(category1);
            assertThrows(IllegalArgumentException.class,
                    () -> user3.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory));
            IPartecipationRequest pr = user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
            assertThrows(IllegalArgumentException.class,
                    () -> user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory));
            assertTrue(user1.getRole(IDesignerRole.class).getPartecipationRequests().contains(pr));
            assertTrue(project1.getTeam().getPartecipationRequests().contains(pr));
            user2.getRole(IProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class,
                    () -> user1.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProjects() {
        try {
            assertTrue(resourceHandler.search(IProject.class, user1.getRole(IDesignerRole.class).getProjects(category1))
                    .contains(project1));
            assertFalse(resourceHandler.search(IProject.class, user1.getRole(IDesignerRole.class).getProjects(category1))
                    .contains(project2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}