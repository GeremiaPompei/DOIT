package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCreateCategory(){
        IResourceHandler resourceHandler = new ResourceHandler();
        UtilityFactory factory = new UtilityFactory(resourceHandler);
        Category category= factory.createCategory("FISICA","descrizione");
        assertTrue(resourceHandler.getRisorse().get(Category.class).contains(category));
    }

}