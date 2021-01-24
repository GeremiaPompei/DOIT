package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.role.PendingRole;
import it.unicam.cs.ids.DOIT.service.entity.*;
import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.Role;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class RHDatabase implements IResourceHandler {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private CategoryRepository categoryRepository;
    private ProjectStateRepository projectStateRepository;
    private final Map<String, String> roles;

    public RHDatabase() {
        this.roles = new HashMap<>();
        this.roles.put("project-proposer", "it.unicam.cs.ids.DOIT.role.ProjectProposerRole");
        this.roles.put("program-manager", "it.unicam.cs.ids.DOIT.role.ProgramManagerRole");
        this.roles.put("designer", "it.unicam.cs.ids.DOIT.role.DesignerRole");
        this.roles.put("project-manager", "it.unicam.cs.ids.DOIT.role.ProjectManagerRole");
    }

    public Set<String> getRolesName() {
        return roles.keySet();
    }

    public String getRolesByName(String key) {
        return roles.get(key);
    }

    @Override
    public <T> void insert(T t) {
        if (User.class.isInstance(t)) {
            userRepository.save(User.class.cast(t));
        } else if (Project.class.isInstance(t)) {
            projectRepository.save(Project.class.cast(t));
        } else if (Category.class.isInstance(t)) {
            categoryRepository.save(Category.class.cast(t));
        } else if (ProjectState.class.isInstance(t)) {
            projectStateRepository.save(ProjectState.class.cast(t));
        }
    }

    @Override
    public <T> void remove(T t) {
        if (User.class.isInstance(t)) {
            //userRepository.delete(User.class.cast(t).getId());
        } else if (Project.class.isInstance(t)) {
            projectRepository.delete(Long.class.cast(t));
        } else if (Category.class.isInstance(t)) {
            categoryRepository.delete(String.class.cast(t));
        } else if (ProjectState.class.isInstance(t)) {
            projectStateRepository.delete(Long.class.cast(t));
        }
    }

    @Override
    public Project getProject(Long id) {
        try {
            return projectRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUser(Long id) {
        try {
            return null;//userRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUser(String email) {
        try {
            return null;//userRepository.findAll().stream().filter(u->u.getEmail().equals(email)).findAny().get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category getCategory(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public ProjectState getProjectState(Long id) {
        return projectStateRepository.findById(id);
    }

    @Override
    public Set<User> getAllUsers() {
        return null;
    }

    @Override
    public Set<User> getUsersByCategoryAndRole(String idCategory, Class<? extends Role> clazz) {
        return null;
    }

    @Override
    public Set<Project> getAllProjects() {
        return null;
    }

    @Override
    public Set<Project> getProjectsByCategory(String idCategory) {
        return null;
    }

    @Override
    public Set<Category> getAllCategories() {
        return Set.copyOf(categoryRepository.findAll());
    }

    @Override
    public Set<Object> getRisorse() {
        return null;
    }

    @Override
    public PartecipationRequest getPartecipationRequest(Long role, Long team, Class<? extends PendingRole> clazz) {
        return null;
    }
}
