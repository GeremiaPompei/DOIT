package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.service.entity.*;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service("repository")
public class Repository implements IResourceHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
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
    public <T> void  insert(T t) {
        if (IUser.class.isInstance(t)) {
            UserEntity ue = new UserEntity();
            ue.fromObject(IUser.class.cast(t));
            userRepository.save(ue);
        } else if (IProject.class.isInstance(t)) {
            ProjectEntity pe = new ProjectEntity();
            pe.fromObject(IProject.class.cast(t));
            projectRepository.save(pe);
        } else if (ICategory.class.isInstance(t)) {
            CategoryEntity ce = new CategoryEntity();
            ce.fromObject(ICategory.class.cast(t));
            categoryRepository.save(ce);
        } else if (ProjectState.class.isInstance(t)) {
            ProjectStateEntity pse = new ProjectStateEntity();
            pse.fromObject(ProjectState.class.cast(t));
            projectStateRepository.save(pse);
        }
    }

    @Override
    public <T> void remove(T t) {
        if (IUser.class.isInstance(t)) {
            UserEntity ue = new UserEntity();
            ue.fromObject(IUser.class.cast(t));
            userRepository.delete(ue);
        } else if (IProject.class.isInstance(t)) {
            ProjectEntity pe = new ProjectEntity();
            pe.fromObject(IProject.class.cast(t));
            projectRepository.delete(pe);
        } else if (ICategory.class.isInstance(t)) {
            CategoryEntity ce = new CategoryEntity();
            ce.fromObject(ICategory.class.cast(t));
            categoryRepository.delete(ce);
        } else if (ProjectState.class.isInstance(t)) {
            ProjectStateEntity pse = new ProjectStateEntity();
            pse.fromObject(ProjectState.class.cast(t));
            projectStateRepository.delete(pse);
        }
    }

    @Override
    public IProject getProject(Long id) {
        try {
            return projectRepository.findById(id).get().toObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IUser getUser(Long id) {
        try {
            return userRepository.findById(id).get().toObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IUser getUser(String email) {
        Iterator<UserEntity> ur;
        try {
            ur = userRepository.findAll().iterator();
        } catch (Exception e) {
            return null;
        }
        while (ur.hasNext()) {
            IUser user = null;
            try {
                user = ur.next().toObject();
            } catch (Exception e) {
                return null;
            }
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    @Override
    public ICategory getCategory(String id) {
        return categoryRepository.findById(id).get().toObject();
    }

    @Override
    public ProjectState getProjectState(Long id) {
        return projectStateRepository.findById(id).get().toObject();
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
