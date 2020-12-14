package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private User user;

    @BeforeEach
    void init() {
        try {
            this.user = new User(7, "Nome", "Cognome", new ArrayList<>());
            Object[] params = {user};
            this.user.addRole(ProjectManagerRole.class, params, User.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user).getUser(), new Category());
        project.setProjectManager(this.user);
        assertEquals(project.getProjectManager(), this.user);
        assertEquals(this.user, project.getProjectManager());
    }

    @Test
    void setTeam() {

    }
}