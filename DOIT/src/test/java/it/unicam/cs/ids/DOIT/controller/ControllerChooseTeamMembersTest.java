package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerChooseTeamMembersTest {
    private User user;
    private ControllerChooseTeamMembers controller;

    @BeforeEach
    void init() {
        try {
            this.user = new User(6, "Nome", "Cognome", new ArrayList<>());
            this.controller = new ControllerChooseTeamMembers();
            this.controller.setUser(this.user);
            Category category = new Category("Sport", "Desc");
            Object[] params = {user, category};
            this.user.addRole(ProgramManagerRole.class, params, User.class, Category.class);
            this.user.addRole(ProjectProposerRole.class, new Object[]{this.user, category}, User.class, Category.class);
            this.user.getRole(ProjectProposerRole.class).createProject(8, "Name", "Description",
                    new Category("Sport", "Desc"));
            this.user.getRole(ProgramManagerRole.class).initTeam(8, this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0));
            this.user.addRole(DesignerRole.class, new Object[]{this.user, category, new CurriculumVitae(new HashMap<>())},
                    User.class,
                    Category.class, CurriculumVitae.class);
            this.user.getRole(DesignerRole.class).createPartecipationRequest(this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0).getTeam());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTeams() {
        try {
            assertEquals(this.controller.getTeams(), this.user.getRole(ProgramManagerRole.class).getTeams());
            assertTrue(this.controller.getTeams() instanceof List);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPartecipationRequests() {
        try {
            Team team = this.controller.getTeams().get(0);
            assertNotNull(this.controller.getPartecipationRequests(team));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addDesigner() {
        try {
            Team team = this.controller.getTeams().get(0);
            assertFalse(this.controller.getPartecipationRequests(team).isEmpty());
            assertTrue(this.controller.addDesigner(this.user.getRole(DesignerRole.class).getPartecipationRequests()
                    .get(0)));
            assertTrue(this.controller.getPartecipationRequests(team).isEmpty());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removePartecipationRequest() {
        try {
            PartecipationRequest pr = this.user.getRole(DesignerRole.class).getPartecipationRequests().get(0);
            String reason = "You are not ready";
            Team team = this.controller.getTeams().get(0);
            assertFalse(this.controller.getPartecipationRequests(team).isEmpty());
            assertTrue(this.controller.removePartecipationRequest(pr, reason));
            assertTrue(this.controller.getPartecipationRequests(team).isEmpty());
            assertEquals(pr.getDescription(), reason);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}