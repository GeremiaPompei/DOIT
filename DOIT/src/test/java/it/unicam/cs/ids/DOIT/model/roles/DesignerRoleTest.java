package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesignerRoleTest {
    private User user1;
    private User user2;
    private User user3;
    private Project project1;
    private Project project2;
    private Category category1;
    private Category category2;
    private IResourceHandler resourceHandler;
    private UtilityFactory factory;

    @BeforeEach
    void init() throws ReflectiveOperationException, RoleException {
        resourceHandler = new ResourceHandler();
        factory = new UtilityFactory(resourceHandler);
        factory.createCategory("Sport", "Descrizione.");
        factory.createCategory("Informatica", "Descrizione.");
        factory.createCategory("Domotica", "Descrizione.");
        user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
        user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
        user3 = factory.createUser(3, "Franca", "Suria", 1994, "Female");
        category1 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("SPORT"));
        category2 = resourceHandler.searchOne(Category.class, x -> x.getName().equals("INFORMATICA"));
        user2.addRole(ProjectProposerRole.class, category1);
        project1 = user2.getRole(ProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
        user2.getRole(ProjectProposerRole.class).getCategories().add(category2);
        project2 = user2.getRole(ProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2, factory);
        user2.addRole(ProgramManagerRole.class, category1);
        user2.getRole(ProjectProposerRole.class).createTeam(user2, project1);
        user1.addRole(DesignerRole.class, category1);
        user3.addRole(DesignerRole.class, category2);
    }

    @Test
    void testCreatePartecipationRequest() {
        try {
            assertNotNull(category1);
            assertThrows(IllegalArgumentException.class,
                    () -> user3.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam()));
            PartecipationRequest pr = user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
            assertThrows(IllegalArgumentException.class,
                    () -> user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam()));
            assertTrue(user1.getRole(DesignerRole.class).getPartecipationRequests().contains(pr));
            assertTrue(project1.getTeam().getPartecipationRequests().contains(pr));
            user2.getRole(ProgramManagerRole.class).addDesigner(pr);
            assertThrows(IllegalArgumentException.class,
                    () -> user1.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProjects() {
        try {
            assertTrue(resourceHandler.search(Project.class, user1.getRole(DesignerRole.class).getProjects(category1))
                    .contains(project1));
            assertFalse(resourceHandler.search(Project.class, user1.getRole(DesignerRole.class).getProjects(category1))
                    .contains(project2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}