package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user1;
    private User user2;
    private Category category;
    private UtilityFactory factory;
    private IResourceHandler resourceHandler;

    @BeforeEach
    private void init() {
        resourceHandler = new ResourceHandler();
        factory = new UtilityFactory(resourceHandler);
        category = factory.createCategory("Sport", "descrizione");
        this.user1 = factory.createUser(1, "Saverio", "Tommasi", 1998, "Male");
        this.user2 = factory.createUser(2, "Mario", "Fartade", 2000, "Male");
    }

    @Test
    void addRole() {
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class, category));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        DesignerRole.class,
                        category));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProgramManagerRole.class,
                        category));

    }

    @Test
    void getRole() {
        assertThrows(RoleException.class, () -> this.user1.getRole(ProjectProposerRole.class));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class,
                        category));
        assertDoesNotThrow(() -> this.user1.getRole(ProjectProposerRole.class));
    }

    @Test
    void removeRole() {
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class,
                        category));
        assertDoesNotThrow(() -> this.user1.getRole(ProjectProposerRole.class));
        assertThrows(RoleException.class,
                ()->{
            user1.getRole(ProjectManagerRole.class);
        });
        this.user1.removeRole(ProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.user1.getRole(ProjectProposerRole.class));
    }
}