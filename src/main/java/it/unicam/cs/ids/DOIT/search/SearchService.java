package it.unicam.cs.ids.DOIT.search;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private RepositoryHandler repositoryHandler;

    public Category getCategoryById(String id) {
        return repositoryHandler.getCategoryRepository().findById(id).get();
    }

    public Set<Category> getAllCategories() {
        Set<Category> categories = new HashSet<>();
        repositoryHandler.getCategoryRepository().findAll().forEach(c -> categories.add(c));
        return categories;
    }

    public User getUserById(Long id) {
        return repositoryHandler.getUserRepository().findById(id).get();
    }

    public Project getProjectById(Long id) {
        return repositoryHandler.getProjectRepository().findById(id).get();
    }

    public Set<User> getUserByKey(String key) {
        Set<User> users = new HashSet<>();
        repositoryHandler.getUserRepository().findAll().forEach(u -> users.add(u));
        return users.stream().filter(u -> u.getName().contains(key) || u.getSurname().contains(key)).collect(Collectors.toSet());
    }

    public Set<Project> getProjectByKey(String key) {
        Set<Project> project = new HashSet<>();
        repositoryHandler.getProjectRepository().findAll().forEach(u -> project.add(u));
        return project.stream().filter(u -> u.getName().contains(key) || u.getDescription().contains(key)).collect(Collectors.toSet());
    }
}
