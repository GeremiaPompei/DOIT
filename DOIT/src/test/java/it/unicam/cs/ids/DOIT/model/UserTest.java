package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    private void init() {
        this.user = new User(6, "Nome", "Cognome", new ArrayList<>());
    }

    @Test
    void addRole() {
        assertDoesNotThrow(() ->
                this.user.addRole(
                        ProjectProposerRole.class,
                        new Object[]{user},
                        User.class));
        assertDoesNotThrow(() ->
                this.user.addRole(
                        DesignerRole.class,
                        new Object[]{user, new CurriculumVitae()},
                        User.class,
                        CurriculumVitae.class));
        assertDoesNotThrow(() ->
                this.user.addRole(
                        ProgramManagerRole.class,
                        new Object[]{user},
                        User.class));
    }

    @Test
    void getRole() {
        assertThrows(RoleException.class, () -> this.user.getRole(ProjectProposerRole.class));
        assertDoesNotThrow(() ->
                this.user.addRole(
                        ProjectProposerRole.class,
                        new Object[]{user},
                        User.class));
        assertDoesNotThrow(() -> this.user.getRole(ProjectProposerRole.class));
    }

    @Test
    void removeRole() {
        assertDoesNotThrow(() ->
                this.user.addRole(
                        ProjectProposerRole.class,
                        new Object[]{user},
                        User.class));
        assertDoesNotThrow(() -> this.user.getRole(ProjectProposerRole.class));
        this.user.removeRole(ProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.user.getRole(ProjectProposerRole.class));
    }
}