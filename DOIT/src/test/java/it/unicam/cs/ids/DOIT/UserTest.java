package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.role.RolesHandler;
import it.unicam.cs.ids.DOIT.model.user.TokenHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    Category category;
    User user1;
    RolesHandler rh;

    @BeforeEach()
    void init() {
        category = new Category("Fisica", "Descrizione");
        user1 = new User("Saverio", "Tommasi", "1998", "Male", "saveriotommasi@gmail.com", "password");
        rh = user1.getRolesHandler(user1.tokenHandlerGet().getToken());
    }

    @Test
    void checkPassword() {
        assertThrows(NullPointerException.class, () -> user1.checkPassword("password1"));
        assertDoesNotThrow(() -> user1.checkPassword("password"));
    }

    @Test
    void tokenHandlerGet() {
        assertNotNull(user1.tokenHandlerGet());
    }

    @Test
    void getRolesHandler() {
        assertEquals(user1.getRolesHandler(user1.tokenHandlerGet().getToken()), rh);
    }

}