package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCreateCategory() {
        IResourceHandler resourceHandler = new ResourceHandler();
        Factory factory = new Factory(resourceHandler);
        ICategory category = factory.createCategory("FISICA", "descrizione");
        assertTrue(resourceHandler.search(ICategory.class, s -> true).contains(category));
    }

}