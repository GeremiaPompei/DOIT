package it.unicam.cs.ids.DOIT.domain.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.simple.model.Category;
import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerRoleTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IUser user4;
    private ICategory category1;
    private ICategory category2;
    private IResourceHandler resourceHandler;
    private IFactoryModel factory;

    @BeforeEach
    void init() throws ReflectiveOperationException {
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
        user4.addRole(ProjectProposerRole.class, category2, factory);
        user1.addRole(ProgramManagerRole.class, category1, factory);
        user3.addRole(ProgramManagerRole.class, category2, factory);
    }

    @Test
    void testCreateProject() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProjectProposerRole.class).createProject(1, "SO", "sistema operativo", category2, factory);
                    }, "L'utente non presenta la categoria: [INFORMATICA]");
            IProject project = user2.getRole(IProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
            assertTrue(user2.getRole(IProjectProposerRole.class).getProjects().contains(project));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFindProgramManagerList() {
        try {
            Set<IUser> userSet = resourceHandler.search(IUser.class, user2.getRole(IProjectProposerRole.class).findProgramManagerList(category1));
            assertTrue(userSet.contains(user1));
            assertFalse(userSet.contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateTeam() {
        try {
            IProject project = user2.getRole(IProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
            assertThrows(RoleException.class,
                    () -> {
                        user2.getRole(IProjectProposerRole.class).createTeam(user2, project, factory);
                    });
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(IProjectProposerRole.class).createTeam(user3, project, factory);
                    });
            assertThrows(RoleException.class,
                    () -> {
                        user2.getRole(IProjectProposerRole.class).createTeam(user4, project, factory);
                    });

            user2.getRole(IProjectProposerRole.class).createTeam(user1, project, factory);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}