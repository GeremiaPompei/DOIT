package it.unicam.cs.ids.DOIT.domain.model;

import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCreateCategory() {
        IResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
        FactoryModel factory = new FactoryModel(resourceHandler);
        ICategory category = factory.createCategory("FISICA", "descrizione");
        assertTrue(resourceHandler.search(ICategory.class, s -> true).contains(category));
    }

}