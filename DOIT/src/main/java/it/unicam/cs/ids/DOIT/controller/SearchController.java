package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.service.SearchService;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return List.copyOf(searchService.getAllCategories());
    }

    @GetMapping(value = "/users-by-key")
    public List<User> getUserByKey(@RequestParam String key) {
        return List.copyOf(searchService.getUserByKey(key));
    }

    @GetMapping(value = "/projects-by-key")
    public List<Project> getProjectsByKey(@RequestParam String key) {
        return List.copyOf(searchService.getProjectByKey(key));
    }
}
