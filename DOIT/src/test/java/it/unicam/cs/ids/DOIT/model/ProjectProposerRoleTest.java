package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerRoleTest {

    private User user;

    @BeforeEach
    void init() {
        try {
            this.user = new User(7, "Nome", "Cognome", new ArrayList<>());
            Object[] params = {user};
            this.user.addRole(ProjectProposerRole.class, params, User.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createProject() {
        try {
            this.user.getRole(ProjectProposerRole.class).createProject(8, "Nome", "Descrizione", new Category());
            assertEquals(this.user.getRole(ProjectProposerRole.class).getProject().get(0).getId(), 8);
            assertEquals(this.user.getRole(ProjectProposerRole.class).getProject().get(0).getName(), "Nome");
            assertEquals(this.user.getRole(ProjectProposerRole.class).getProject().get(0).getDescription(), "Descrizione");
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void becomeProgramManager() {
        try {
            this.user.addRole(ProjectProposerRole.class, new Object[]{this.user}, User.class);
            assertThrows(RoleException.class, () -> this.user.getRole(ProgramManagerRole.class));
            this.user.getRole(ProjectProposerRole.class).becomeProgramManager();
            assertDoesNotThrow(() -> this.user.getRole(ProgramManagerRole.class));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}