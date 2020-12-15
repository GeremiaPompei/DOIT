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
            this.user.addRole(ProjectProposerRole.class, new Object[]{this.user}, User.class);
            this.user.getRole(ProjectProposerRole.class).createProject(8, "Name", "Description",
                    new Category());
            this.user.getRole(ProgramManagerRole.class).initTeam(8, this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0));
            this.user.addRole(DesignerRole.class, new Object[]{this.user, new CurriculumVitae()}, User.class,
                    CurriculumVitae.class);
            this.user.getRole(DesignerRole.class).createPartecipationRequest(this.user.getRole(ProjectProposerRole.class)
                    .getProjects().get(0).getTeam());
        } catch (Exception e) {
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
            assertTrue(this.user.getRole(ProgramManagerRole.class).getTeams().get(0).getDesigners().contains(pr.getUser()));
            assertTrue(pr.getState());
            assertTrue(this.user.getRole(ProgramManagerRole.class).removeDesigner(this.user, pr.getTeam()));
            assertFalse(this.user.getRole(ProgramManagerRole.class).getTeams().get(0).getDesigners().contains(pr.getUser()));
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