package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourceHandler implements IResourceHandler {

    private final Set<Object> risorse;
    private final Map<String, String> roles;

    ResourceHandler() {
        this.risorse = new HashSet<>();
        this.roles = new HashMap<>();
        this.roles.put("project-proposer", "it.unicam.cs.ids.DOIT.role.ProjectProposerRole");
        this.roles.put("program-manager", "it.unicam.cs.ids.DOIT.role.ProgramManagerRole");
        this.roles.put("designer", "it.unicam.cs.ids.DOIT.role.DesignerRole");
        this.roles.put("project-manager", "it.unicam.cs.ids.DOIT.role.ProjectManagerRole");
    }

    private <T> T searchOne(Class<T> clazz, Predicate<T> p) {
        return clazz.cast(risorse.stream().filter(clazz::isInstance).map(clazz::cast).filter(p)
                .findAny().orElse(null));
    }

    private <T> Set<T> search(Class<T> clazz, Predicate<T> p) {
        return risorse.stream().filter(clazz::isInstance).map(clazz::cast)
                .filter(p).collect(Collectors.toSet());
    }

    public Set<String> getRolesName() {
        return roles.keySet();
    }

    public String getRolesByName(String key) {
        return roles.get(key);
    }

    public IProject getProject(int id) {
        return searchOne(IProject.class, p -> p.getId() == id);
    }

    public ProjectState getProjectState(int id) {
        return searchOne(ProjectState.class, p -> p.getId() == id);
    }

    public IUser getUser(int id) {
        return searchOne(IUser.class, p -> p.getId() == id);
    }

    @Override
    public IUser getUser(String email) {
        return searchOne(IUser.class, u -> u.getEmail().equals(email));
    }

    @Override
    public Set<IUser> getAllUsers() {
        return search(IUser.class, t -> true);
    }

    public Set<IUser> getUsersByCategoryAndRole(String idCategory, Class<? extends IRole> clazz) {
        return search(IUser.class, u -> {
            try {
                return u.getRole(clazz).getCategories().contains(getCategory(idCategory));
            } catch (RoleException e) {
                return false;
            }
        });
    }

    public ICategory getCategory(String id) {
        return searchOne(ICategory.class, p -> p.getName().equalsIgnoreCase(id));
    }

    @Override
    public Set<IProject> getAllProjects() {
        return search(IProject.class, t -> true);
    }

    @Override
    public Set<IProject> getProjectsByCategory(String idCategory) {
        return search(IProject.class, p -> p.getCategory().getName().equalsIgnoreCase(idCategory));
    }

    @Override
    public Set<ICategory> getAllCategories() {
        return search(ICategory.class, t -> true);
    }

    @Override
    public <T> void insert(T t) {
        this.risorse.add(t);
    }

    @Override
    public <T> void remove(T t) {
        this.risorse.remove(t);
    }

    public Set<Object> getRisorse() {
        return risorse;
    }
}
