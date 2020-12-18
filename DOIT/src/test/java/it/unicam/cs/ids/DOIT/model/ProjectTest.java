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
            Object[] params = {user, new Category("Sport", "Desc")};
            this.user.addRole(ProjectManagerRole.class, params, User.class, Category.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        Category category = new Category("Sport", "Desc");
        Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user,
                category).getUser(), category);
        project.setProjectManager(this.user);
        assertEquals(project.getProjectManager(), this.user);
        assertEquals(this.user, project.getProjectManager());
    }

    @Test
    void setTeam() {

    }
}