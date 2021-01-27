
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProjectStateTest {

    ProjectState a;
    ProjectState b;
    ProjectState c;

    @BeforeEach
    void init() {
        a = new ProjectState((long) 0, "INIZIALIZZAZIONE", "Stato iniziale.");
        b = new ProjectState((long) 1, "SVILUPPO", "Stato di sviluppo.");
        c = new ProjectState((long) 2, "TERMINALE", "Stato terminale.");
    }

    @Test
    void getId() {
        assertEquals(a.getId(), 0);
        assertNotEquals(b.getId(), 0);
    }

    @Test
    void getName() {
        assertEquals(a.getName(), "INIZIALIZZAZIONE");
        assertNotEquals(b.getName(), "INIZIALIZZAZIONE");
    }

    @Test
    void getDescription() {
        assertEquals(a.getDescription(), "Stato iniziale.");
        assertNotEquals(b.getDescription(), "Stato iniziale.");
    }
}
