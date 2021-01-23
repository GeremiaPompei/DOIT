package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.role.IPendingRole;
import it.unicam.cs.ids.DOIT.service.entity.*;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.user.IUser;

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
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            Statement statement = connection.createStatement();
            userRepository = new UserRepository(statement);
        } catch (SQLException throwables) {
        }
    }

    public Set<String> getRolesName() {
        return roles.keySet();
    }

    public String getRolesByName(String key) {
        return roles.get(key);
    }

    @Override
    public <T> void insert(T t) {
        if (IUser.class.isInstance(t)) {
            userRepository.save(IUser.class.cast(t));
        } else if (IProject.class.isInstance(t)) {
            projectRepository.save(IProject.class.cast(t));
        } else if (ICategory.class.isInstance(t)) {
            categoryRepository.save(ICategory.class.cast(t));
        } else if (ProjectState.class.isInstance(t)) {
            projectStateRepository.save(ProjectState.class.cast(t));
        }
    }

    @Override
    public <T> void remove(T t) {
        if (IUser.class.isInstance(t)) {
            userRepository.delete(IUser.class.cast(t).getId());
        } else if (IProject.class.isInstance(t)) {
            projectRepository.delete(Long.class.cast(t));
        } else if (ICategory.class.isInstance(t)) {
            categoryRepository.delete(String.class.cast(t));
        } else if (ProjectState.class.isInstance(t)) {
            projectStateRepository.delete(Long.class.cast(t));
        }
    }

    @Override
    public IProject getProject(Long id) {
        try {
            return projectRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IUser getUser(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IUser getUser(String email) {
        try {
            return userRepository.findAll().stream().filter(u->u.getEmail().equals(email)).findAny().get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ICategory getCategory(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public ProjectState getProjectState(Long id) {
        return projectStateRepository.findById(id);
    }

    @Override
    public Set<IUser> getAllUsers() {
        return null;
    }

    @Override
    public Set<IUser> getUsersByCategoryAndRole(String idCategory, Class<? extends IRole> clazz) {
        return null;
    }

    @Override
    public Set<IProject> getAllProjects() {
        return null;
    }

    @Override
    public Set<IProject> getProjectsByCategory(String idCategory) {
        return null;
    }

    @Override
    public Set<ICategory> getAllCategories() {
        return Set.copyOf(categoryRepository.findAll());
    }

    @Override
    public Set<Object> getRisorse() {
        return null;
    }

    @Override
    public IPartecipationRequest getPartecipationRequest(Long role, Long team, Class<? extends IPendingRole> clazz) {
        return null;
    }
}
