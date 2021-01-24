package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.service.entity.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class Search implements ISearch {
    @Autowired
    CategoryRepository categoryRepository;

    public Set<Category> getAllCategories() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().forEach(c -> categories.add(c));
        return categories;
    }
}
