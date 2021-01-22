package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.service.entity.CategoryRepository;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.service.entity.ProjectStateRepository;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Repository implements IResourceHandler {
    /*@Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;*/
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    private final Map<String, String> roles;

    public Repository() {
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
        /*if (User.class.equals(t.getClass())) {
            userRepository.save((User) t);
        } else if (Project.class.equals(t.getClass())) {
            projectRepository.save((Project) t);
        } else*/ if (Category.class.equals(t.getClass())) {
            categoryRepository.save((Category) t);
        } else if (ProjectState.class.equals(t.getClass())) {
            projectStateRepository.save((ProjectState) t);
        }
    }

    @Override
    public <T> void remove(T t) {
        /*if (User.class.equals(t.getClass())) {
            userRepository.delete((User) t);
        } else if (Project.class.equals(t.getClass())) {
            projectRepository.delete((Project) t);
        } else*/ if (Category.class.equals(t.getClass())) {
            categoryRepository.delete((Category) t);
        } else if (ProjectState.class.equals(t.getClass())) {
            projectStateRepository.delete((ProjectState) t);
        }
    }

    @Override
    public IProject getProject(Long id) {
        return null;//projectRepository.findById(id).get();
    }

    @Override
    public IUser getUser(Long id) {
        return null;//userRepository.findById(id).get();
    }

    @Override
    public IUser getUser(String email) {
        /*Iterator<User> ur = userRepository.findAll().iterator();
        while (ur.hasNext()) {
            User user = ur.next();
            if (user.getEmail().equals(email))
                return user;
        }*/
        return null;
    }

    @Override
    public ICategory getCategory(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public ProjectState getProjectState(Long id) {
        return projectStateRepository.findById(id).get();
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
        return null;
    }

    @Override
    public Set<Object> getRisorse() {
        return null;
    }
}
