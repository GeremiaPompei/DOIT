package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {


    @Test
    void testCreateCategory() {
        Category category =new Category("Fisica", "Descrizione");
        assertNotNull(category);
    }

    @Test
    void getName() {
        Category category = new Category("Fisica", "Descrizione");
        assertEquals(category.getName(), "Fisica");
        assertNotEquals(category.getName(), "Sport");
    }

    @Test
    void getDescription() {
        Category category = new Category("Fisica", "Descrizione");
        assertNotEquals(category.getDescription(), "Descrizione.");
        assertEquals(category.getDescription(), "Descrizione");
        assertNotEquals(category.getName(), "Descrizione");
    }
}

