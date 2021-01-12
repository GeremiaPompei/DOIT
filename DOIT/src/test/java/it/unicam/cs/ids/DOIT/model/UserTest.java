package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.IProjectManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.IProjectProposerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.storage.FactoryStorage;
import it.unicam.cs.ids.DOIT.storage.IResourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private IUser user1;
    private IUser user2;
    private ICategory category;
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;

    @BeforeEach
    private void init() {
        resourceHandler = FactoryStorage.getResouceHandler();
        resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
        factory = new FactoryModel();
        category = factory.createCategory("Sport", "descrizione");
        this.user1 = factory.createUser(1, "Saverio", "Tommasi", 1998, "Male");
        this.user2 = factory.createUser(2, "Mario", "Fartade", 2000, "Male");
    }

    @Test
    void addRole() {
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class, category, factory));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        DesignerRole.class,
                        category, factory));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProgramManagerRole.class,
                        category, factory));

    }

    @Test
    void getRole() {
        assertThrows(RoleException.class, () -> this.user1.getRole(IProjectProposerRole.class));
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class,
                        category, factory));
        assertDoesNotThrow(() -> this.user1.getRole(IProjectProposerRole.class));
    }

    @Test
    void removeRole() {
        assertDoesNotThrow(() ->
                this.user1.addRole(
                        ProjectProposerRole.class,
                        category, factory));
        assertDoesNotThrow(() -> this.user1.getRole(IProjectProposerRole.class));
        assertThrows(RoleException.class,
                ()->{
            user1.getRole(IProjectManagerRole.class);
        });
        this.user1.removeRole(IProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.user1.getRole(IProjectProposerRole.class));
    }
}