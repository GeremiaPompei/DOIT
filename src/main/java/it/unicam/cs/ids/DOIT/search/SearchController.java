package it.unicam.cs.ids.DOIT.search;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.user.User;
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

    @GetMapping(value = "/category-by-id")
    public Category getCategoryById(@RequestParam String id) {
        return searchService.getCategoryById(id);
    }

    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return List.copyOf(searchService.getAllCategories());
    }

    @GetMapping(value = "/user-by-id")
    public User getUserById(@RequestParam Long id) {
        return searchService.getUserById(id);
    }

    @GetMapping(value = "/project-by-id")
    public Project getProjectById(@RequestParam Long id) {
        return searchService.getProjectById(id);
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
