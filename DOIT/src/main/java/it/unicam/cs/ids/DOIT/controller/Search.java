package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("search")
public class Search implements ISearch {
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    public Set<Category> getAllCategories() {
        return servicesHandler.getResourceHandler().getAllCategories();
    }
}
