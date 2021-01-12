package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.storage.FactoryStorage;
import it.unicam.cs.ids.DOIT.storage.IResourceHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCreateCategory() {
        IResourceHandler resourceHandler = FactoryStorage.getResouceHandler();
        resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
        FactoryModel factory = new FactoryModel();
        ICategory category = factory.createCategory("FISICA", "descrizione");
        assertTrue(resourceHandler.search(ICategory.class, s -> true).contains(category));
    }

}