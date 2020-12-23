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
        assertDoesNotThrow(() -> this.controller.becomeProgramManager(this.project));
        this.controller.getUser().removeRole(ProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.controller.becomeProgramManager(this.project));
    }

    @Test
    void findProgramManagerList() {

    }

    @Test
    void initTeam() {
        User user2 = new User(9, "Nome2", "Cognome2", new ArrayList<>());
        assertThrows(RoleException.class, () -> this.controller.initTeam(this.project));
        assertDoesNotThrow(() -> user2.addRole(ProgramManagerRole.class, new Category("Sport", "Desc")));
        this.controller.setUser(user2);
        assertDoesNotThrow(() -> this.controller.initTeam(this.project));
    }
}