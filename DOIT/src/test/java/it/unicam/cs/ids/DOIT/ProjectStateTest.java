package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectStateTest {

    @BeforeEach
    void init() {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");

    }
    @Test
    void getId() {
        assertEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(0).getId(), 0);
        assertNotEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(1).getId(), 0);

    }

    @Test
    void getName() {
        assertEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(0).getName(), "INIZIALIZZAZIONE");
        assertNotEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(1).getName(), "INIZIALIZZAZIONE");
    }

    @Test
    void getDescription() {
        assertEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(0).getDescription(), "Stato iniziale.");
        assertNotEquals(ServicesHandler.getInstance().getResourceHandler().getProjectState(1).getDescription(), "Stato iniziale.");
    }
}