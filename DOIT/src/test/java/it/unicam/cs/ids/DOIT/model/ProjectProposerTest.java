package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerTest {

    private User user;

    @BeforeEach
    void init() {
        this.user = new User(7, "Nome", "Cognome", new ArrayList<>());
        this.user.getRolesHandler().initProjectProposer();
    }

    @Test
    void createProject() {
        try {
            this.user.getRolesHandler().getProjectProposer().createProject(8, "Nome", "Descrizione",  new Category());
            assertEquals(this.user.getRolesHandler().getProjectProposer().getProject().get(0).getId(), 8);
            assertEquals(this.user.getRolesHandler().getProjectProposer().getProject().get(0).getName(), "Nome");
            assertEquals(this.user.getRolesHandler().getProjectProposer().getProject().get(0).getDescription(), "Descrizione");
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }


}