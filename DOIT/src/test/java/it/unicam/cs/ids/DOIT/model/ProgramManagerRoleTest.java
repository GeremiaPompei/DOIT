package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProgramManagerRoleTest {
    private User user;
    private User user2;

    @BeforeEach
    void init() {
        try {
            this.user = new User(6, "Nome", "Cognome", new ArrayList<>());
            this.user2 = new User(7, "Nome", "Cognome", new ArrayList<>());
            Object[] params = {user};
            Object[] params2 = {user};
            this.user.addRole(ProgramManagerRole.class, params, User.class);
            this.user2.addRole(ProgramManagerRole.class, params2, User.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        try {
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user).getUser(), new Category());
            this.user.getRole(ProgramManagerRole.class).setProjectManager(user2, project);
            assertEquals(project.getProjectManager(), this.user2);
            assertEquals(this.user2, project.getProjectManager());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initTeam() {
        try {
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user).getUser(), new Category());
            this.user.getRole(ProgramManagerRole.class).initTeam(5, project);
            Team team = this.user.getRole(ProgramManagerRole.class).getTeam(5);
            assertTrue(this.user.getRole(ProgramManagerRole.class).getTeams().contains(team));
            assertEquals(project.getTeam(), team);
            assertEquals(project, team.getProject());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}