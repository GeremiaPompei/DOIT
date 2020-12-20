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
            Category category = new Category("Sport", "Desc");
            this.user.addRole(ProgramManagerRole.class, category);
            this.user2.addRole(ProgramManagerRole.class, category);
            this.user.addRole(ProjectProposerRole.class, category);
            this.user.getRole(ProjectProposerRole.class).createProject(8, "Name", "Description",
                    new Category("Sport", "Desc"));
            this.user.getRole(ProgramManagerRole.class).initTeam(8, this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0));
            this.user.addRole(DesignerRole.class, category);
            this.user.getRole(DesignerRole.class).createPartecipationRequest(this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0).getTeam());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setProjectManager() {
        try {
            Category category = new Category("Sport", "Desc");
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user,
                    category).getUser(), category);
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
            Category category = new Category("Sport", "Desc");
            Project project = new Project(8, "Nome", "Descrizione", new ProjectProposerRole(user,
                    category).getUser(), category);
            this.user.getRole(ProgramManagerRole.class).initTeam(5, project);
            Team team = this.user.getRole(ProgramManagerRole.class).getTeam(5);
            assertTrue(this.user.getRole(ProgramManagerRole.class).getTeams().contains(team));
            assertEquals(project.getTeam(), team);
            assertEquals(project, team.getProject());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removePartecipationRequest() {
        try {
            PartecipationRequest pr = this.user.getRole(DesignerRole.class).getPartecipationRequests().get(0);
            assertNotNull(pr);
            String description = "You're not ready";
            this.user.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, description);
            assertNotNull(pr.getTeam());
            assertFalse(pr.getTeam().getPartecipationRequests().contains(pr));
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), description);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeDesigner() {
        try {
            PartecipationRequest pr = this.user.getRole(DesignerRole.class).getPartecipationRequests().get(0);
            assertTrue(this.user.getRole(ProgramManagerRole.class).addDesigner(pr));
            assertTrue(this.user.getRole(ProgramManagerRole.class).getTeams().get(0).getDesigners().contains(pr.getDesigner()));
            assertTrue(pr.getState());
            assertTrue(this.user.getRole(ProgramManagerRole.class).removeDesigner(this.user, pr.getTeam()));
            assertFalse(this.user.getRole(ProgramManagerRole.class).getTeams().get(0).getDesigners().contains(pr.getDesigner()));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addDesigner() {
        try {
            PartecipationRequest pr = this.user.getRole(DesignerRole.class).getPartecipationRequests().get(0);
            assertTrue(this.user.getRole(ProgramManagerRole.class).addDesigner(pr));
            assertTrue(this.user.getRole(ProgramManagerRole.class).getTeams().get(0).getDesigners().contains(this.user));
            assertTrue(pr.getState());
            assertEquals(pr.getDescription(), "Congratulations! You are accepted.");
        } catch (RoleException e) {
            e.printStackTrace();
        }


    }
}