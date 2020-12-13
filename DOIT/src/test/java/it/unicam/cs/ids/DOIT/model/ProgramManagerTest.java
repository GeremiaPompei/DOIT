package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProgramManagerTest {
    private User user;

    @BeforeEach
    void init() {
        this.user = new User(6, "Nome", "Cognome", new ArrayList<>());
        this.user.getRolesHandler().initProgramManager();
    }


    @Test
    void initTeam() {
        try {
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposer(user), new Category());
            this.user.getRolesHandler().getProgramManager().initTeam(5, project, user);
            Team team = this.user.getRolesHandler().getProgramManager().getTeam(5);
            assertTrue(this.user.getRolesHandler().getProgramManager().getTeams().contains(team));
            assertEquals(project.getTeam(), team);
            assertEquals(project, team.getProject());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}