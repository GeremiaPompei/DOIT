package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerChooseProgramManagerTest {

    private ControllerChooseProgramManager controller;
    private Project project;

    @BeforeEach
    void init() {
        this.controller = new ControllerChooseProgramManager();
        User user = new User(7, "Nome", "Cognome", new ArrayList<>());
        this.controller.setUser(user);
        try {
            user.addRole(ProjectProposerRole.class, new Category("Sport", "Desc"));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        this.project = new Project(8, "Nome", "Descrizione", user, new Category("Sport", "Desc"));
    }


    @Test
    void iAmProgramManager() {
        assertDoesNotThrow(() -> this.controller.becomeProgramManager(3, this.project));
        this.controller.getUser().removeRole(ProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.controller.becomeProgramManager(4, this.project));
    }

    @Test
    void findProgramManagerList() {

    }

    @Test
    void itIsProgramManager() {
        User user2 = new User(9, "Nome2", "Cognome2", new ArrayList<>());
        assertThrows(RoleException.class, () -> this.controller.itIsProgramManager(user2, 2, this.project));
        assertDoesNotThrow(() -> user2.addRole(ProgramManagerRole.class, new Category("Sport", "Desc")));
        assertDoesNotThrow(() -> this.controller.itIsProgramManager(user2, 89, this.project));
    }
}