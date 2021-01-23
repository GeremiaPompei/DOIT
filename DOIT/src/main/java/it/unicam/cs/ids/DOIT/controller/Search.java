package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("search")
public class Search implements ISearch {
    @Autowired
    private ServicesHandler servicesHandler;

    public Set<ICategory> getAllCategories() {
        return servicesHandler.getResourceHandler().getAllCategories();
    }
}
