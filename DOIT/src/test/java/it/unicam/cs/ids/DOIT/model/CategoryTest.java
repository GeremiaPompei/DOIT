package it.unicam.cs.ids.DOIT.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCreateCategory(){
        GestoreRisorse.getInstance().clear();
        Category category= new Category("FISICA","descrizione");
        assertTrue(GestoreRisorse.getInstance().getRisorse().get(Category.class).contains(category));
    }

}