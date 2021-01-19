/*
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.role.RoleException;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import it.unicam.cs.ids.DOIT.user.IUserHandler;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {
    private IUserHandler userHandler;
    private IUser user1;
    private IUser user2;
    private ICategory category;

    @BeforeEach
    private void init() {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
        userHandler = new UserHandler();
        category = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Saverio", "Tommasi", "1998", "Male");
        user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Mario", "Fartade", "2000", "Male");
    }

    @Test
    void login() {
        userHandler.logIn(user1.getId());
        assertEquals(userHandler.getUser(), user1);
        userHandler.logIn(user2.getId());
        assertEquals(userHandler.getUser(), user2);
    }

    @Test
    void signIn() {
        userHandler.signIn("Pippo", "Pluto", "1999/08/08", "Male");
        assertNotNull(ServicesHandler.getInstance().getResourceHandler().getAllUsers().stream().findFirst().get());
    }

    @Test
    void addRole() {
        try {
            userHandler.logIn(user1.getId());
            assertEquals(userHandler.getUser(), user1);
            userHandler.addRole("ProjectProposerRole", "sport");
            assertNotNull(userHandler.getUser().getRole(ProjectProposerRole.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeRole() {
        try {
            userHandler.logIn(user1.getId());
            assertEquals(userHandler.getUser(), user1);
            userHandler.addRole("ProjectProposerRole", "sport");
            assertNotNull(userHandler.getUser().getRole(ProjectProposerRole.class));
            userHandler.removeRole("ProjectProposerRole");
            assertThrows(RoleException.class, () -> userHandler.getUser().getRole(ProjectProposerRole.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRole() {
        try {
            userHandler.logIn(user1.getId());
            assertEquals(userHandler.getUser(), user1);
            userHandler.addRole("ProjectProposerRole", "sport");
            assertNotNull(userHandler.getUser().getRole(ProjectProposerRole.class));
            assertNotNull(userHandler.getRole("ProjectProposerRole"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void logOut() {
        userHandler.logIn(user1.getId());
        assertEquals(userHandler.getUser(), user1);
        userHandler.logOut();
        assertNull(userHandler.getUser());
    }
}*/
