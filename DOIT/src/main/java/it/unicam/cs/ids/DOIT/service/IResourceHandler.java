package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.role.PendingRole;
import it.unicam.cs.ids.DOIT.role.Role;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.Set;

public interface IResourceHandler {
    Set<String> getRolesName();

    String getRolesByName(String key);

    <T> void insert(T t);

    <T> void remove(T t);

    Project getProject(Long id);

    User getUser(Long id);

    User getUser(String email);

    Category getCategory(String id);

    ProjectState getProjectState(Long id);

    Set<User> getAllUsers();

    Set<User> getUsersByCategoryAndRole(String idCategory, Class<? extends Role> clazz);

    Set<Project> getAllProjects();

    Set<Project> getProjectsByCategory(String idCategory);

    Set<Category> getAllCategories();

    Set<Object> getRisorse();

    PartecipationRequest getPartecipationRequest(Long role, Long team, Class<? extends PendingRole> clazz);
}
