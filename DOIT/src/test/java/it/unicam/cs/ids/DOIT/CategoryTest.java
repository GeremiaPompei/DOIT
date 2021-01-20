/*

package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @BeforeEach
    void init() {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
    }

    @Test
    void testCreateCategory() {
        ICategory category = ServicesHandler.getInstance().getFactoryModel().createCategory("Fisica", "Descrizione.");
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllCategories().contains(category));
    }

    @Test
    void getName() {
        ICategory category = ServicesHandler.getInstance().getFactoryModel().createCategory("Fisica", "Descrizione.");
        assertEquals(category.getName(), "Fisica");
        assertNotEquals(category.getName(), "Sport");
    }

    @Test
    void getDescription() {
        ICategory category = ServicesHandler.getInstance().getFactoryModel().createCategory("Fisica", "Descrizione.");
        assertEquals(category.getDescription(), "Descrizione.");
        assertNotEquals(category.getName(), "Descrizione");
    }
}
*/
