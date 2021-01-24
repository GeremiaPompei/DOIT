package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RHSet implements IResourceHandler {

    private final Set<Object> risorse;
    private final Map<String, String> roles;

    RHSet() {
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

    public Project getProject(Long id) {
        return searchOne(Project.class, p -> p.getId() == id);
    }

    public ProjectState getProjectState(Long id) {
        return searchOne(ProjectState.class, p -> p.getId() == id);
    }

    public User getUser(Long id) {
        return searchOne(User.class, p -> p.getId().equals(id));
    }

    @Override
    public User getUser(String email) {
        return searchOne(User.class, u -> u.getEmail().equals(email));
    }

    @Override
    public Set<User> getAllUsers() {
        return search(User.class, t -> true);
    }

    public Set<User> getUsersByCategoryAndRole(String idCategory, Class<? extends Role> clazz) {
        return search(User.class, u -> {
            try {
                return u.getRole(clazz).getCategories().contains(getCategory(idCategory));
            } catch (RoleException e) {
                return false;
            }
        });
    }

    public Category getCategory(String id) {
        return searchOne(Category.class, p -> p.getName().equalsIgnoreCase(id));
    }

    @Override
    public Set<Project> getAllProjects() {
        return search(Project.class, t -> true);
    }

    @Override
    public Set<Project> getProjectsByCategory(String idCategory) {
        return search(Project.class, p -> p.getCategory().getName().equalsIgnoreCase(idCategory));
    }

    @Override
    public Set<Category> getAllCategories() {
        return search(Category.class, t -> true);
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

    @Override
    public PartecipationRequest getPartecipationRequest(Long role, Long team, Class<? extends PendingRole> clazz) {
        return searchOne(PartecipationRequest.class, pr -> pr.getPendingRole().equals(role) && pr.getTeam().equals(team)
                && clazz.isInstance(pr.getPendingRole()));
    }
}
