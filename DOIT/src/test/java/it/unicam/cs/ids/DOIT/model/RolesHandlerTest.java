package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RolesHandlerTest {
    private User user;

    @BeforeEach
    void init() {
        this.user = new User(6, "Nome", "Cognome", new ArrayList<>());
    }

    @Test
    void getProgramManager() {
        this.user.getRolesHandler().initProgramManager();
        try {
            assertTrue(this.user.getRolesHandler().getProgramManager() instanceof ProgramManager);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initProgramManager() {
        assertThrows(RoleException.class, () -> this.user.getRolesHandler().getProgramManager());
        this.user.getRolesHandler().initProgramManager();
        assertDoesNotThrow(() -> this.user.getRolesHandler().getProgramManager());
    }
}