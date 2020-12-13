package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private User user;

    @BeforeEach
    void init() {
        this.user = new User(7, "Nome", "Cognome", new ArrayList<>());
        this.user.getRolesHandler().initProjectManager();
    }

    @Test
    void setProjectManager() {
        try {
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposer(user).getUser(), new Category());
            project.setProjectManager(this.user);
            assertEquals(project.getProjectManager(), this.user);
            assertEquals(this.user, project.getProjectManager());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setTeam() {

    }
}