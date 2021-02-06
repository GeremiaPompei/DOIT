package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.role.RolesHandler;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    Category category;
    User user1;
    RolesHandler rh;

    @BeforeEach()
    void init() {
        category = new Category("Fisica", "Descrizione");
        user1 = new User("Saverio", "Tommasi", LocalDate.of(1999, 9, 9), "Male", "saveriotommasi@gmail.com", "password");
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