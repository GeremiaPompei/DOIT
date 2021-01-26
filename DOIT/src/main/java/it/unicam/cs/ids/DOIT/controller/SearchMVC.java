package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchMVC {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public Set<Category> getAllCategories() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().forEach(c -> categories.add(c));
        return categories;
    }

    public Set<User> getUserByKey(String key) {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(u -> users.add(u));
        return users.stream().filter(u -> u.getName().contains(key) || u.getSurname().contains(key)).collect(Collectors.toSet());
    }

    public Set<Project> getProjectByKey(String key) {
        Set<Project> project = new HashSet<>();
        projectRepository.findAll().forEach(u -> project.add(u));
        return project.stream().filter(u -> u.getName().contains(key) || u.getDescription().contains(key)).collect(Collectors.toSet());
    }
}
