package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.role.*;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RolesHandlerTest {

    Category category;
    User user1;
    RolesHandler rh;

    @BeforeEach()
    void init() {
        category = new Category("Fisica", "Descrizione");
        user1 = new User("Saverio", "Tommasi", LocalDate.of(1999,9,9), "Male", "saveriotommasi@gmail.com", "password");
        rh = user1.getRolesHandler(user1.tokenHandlerGet().getToken());
    }

    @Test
    void getProjectProposerRole() {
        assertNull(rh.getProjectProposerRole());
        rh.addRole(ProjectProposerRole.TYPE, category);
        assertNotNull(rh.getProjectProposerRole());
    }

    @Test
    void getProgramManagerRole() {
        assertNull(rh.getProgramManagerRole());
        rh.addRole(ProgramManagerRole.TYPE, category);
        assertNotNull(rh.getProgramManagerRole());
    }

    @Test
    void getDesignerRole() {
        assertNull(rh.getDesignerRole());
        rh.addRole(DesignerRole.TYPE, category);
        assertNotNull(rh.getDesignerRole());
    }

    @Test
    void getProjectManagerRole() {
        assertNull(rh.getProjectManagerRole());
        rh.addRole(ProjectManagerRole.TYPE, category);
        assertNotNull(rh.getProjectManagerRole());
    }

    @Test
    void addProjectProposerRole() {
        assertNull(rh.getProjectProposerRole());
        rh.addRole(ProjectProposerRole.TYPE, category);
        assertNotNull(rh.getProjectProposerRole());
    }

    @Test
    void addProgramManagerRole() {
        assertNull(rh.getProgramManagerRole());
        rh.addRole(ProgramManagerRole.TYPE, category);
        assertNotNull(rh.getProgramManagerRole());
    }

    @Test
    void addDesignerRole() {
        assertNull(rh.getDesignerRole());
        rh.addRole(DesignerRole.TYPE, category);
        assertNotNull(rh.getDesignerRole());
    }

    @Test
    void addProjectManagerRole() {
        assertNull(rh.getProjectManagerRole());
        rh.addRole(ProjectManagerRole.TYPE, category);
        assertNotNull(rh.getProjectManagerRole());
    }

    @Test
    void removeProjectProposerRole() {
        assertNull(rh.getProjectProposerRole());
        rh.addRole(ProjectProposerRole.TYPE, category);
        assertNotNull(rh.getProjectProposerRole());
        rh.removeRole(ProjectProposerRole.TYPE);
        assertNull(rh.getProjectProposerRole());
    }

    @Test
    void removeProgramManagerRole() {
        assertNull(rh.getProgramManagerRole());
        rh.addRole(ProgramManagerRole.TYPE, category);
        assertNotNull(rh.getProgramManagerRole());
        rh.removeRole(ProgramManagerRole.TYPE);
        assertNull(rh.getProgramManagerRole());
    }

    @Test
    void removeDesignerRole() {
        assertNull(rh.getDesignerRole());
        rh.addRole(DesignerRole.TYPE, category);
        assertNotNull(rh.getDesignerRole());
        rh.removeRole(DesignerRole.TYPE);
        assertNull(rh.getDesignerRole());
    }

    @Test
    void removeProjectManagerRole() {
        assertNull(rh.getProjectManagerRole());
        rh.addRole(ProjectManagerRole.TYPE, category);
        assertNotNull(rh.getProjectManagerRole());
        rh.removeRole(ProjectManagerRole.TYPE);
        assertNull(rh.getProjectManagerRole());
    }

    @Test
    void isProjectProposer() {
        assertFalse(rh.isProjectProposer());
        rh.addRole(ProjectProposerRole.TYPE, category);
        assertTrue(rh.isProjectProposer());
    }

    @Test
    void isProgramManager() {
        assertFalse(rh.isProgramManager());
        rh.addRole(ProgramManagerRole.TYPE, category);
        assertTrue(rh.isProgramManager());
    }

    @Test
    void isDesigner() {
        assertFalse(rh.isDesigner());
        rh.addRole(DesignerRole.TYPE, category);
        assertTrue(rh.isDesigner());
    }

    @Test
    void isProjectManager() {
        assertFalse(rh.isProjectManager());
        rh.addRole(ProjectManagerRole.TYPE, category);
        assertTrue(rh.isProjectManager());
    }
}