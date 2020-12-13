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

    @Test
    void getProjectManager() {
        this.user.getRolesHandler().initProjectManager();
        try {
            assertTrue(this.user.getRolesHandler().getProjectManager() instanceof ProjectManager);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initProjectManager() {
        assertThrows(RoleException.class, () -> this.user.getRolesHandler().getProjectManager());
        this.user.getRolesHandler().initProjectManager();
        assertDoesNotThrow(() -> this.user.getRolesHandler().getProjectManager());
    }

    @Test
    void getProjectProposer() {
        this.user.getRolesHandler().initProjectProposer();
        try {
            assertTrue(this.user.getRolesHandler().getProjectProposer() instanceof ProjectProposer);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initProjectProposer() {
        assertThrows(RoleException.class, () -> this.user.getRolesHandler().getProjectProposer());
        this.user.getRolesHandler().initProjectProposer();
        assertDoesNotThrow(() -> this.user.getRolesHandler().getProjectProposer());
    }
}