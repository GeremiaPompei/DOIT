package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;

public interface IResourceHandler {
    Set<String> getRolesName();

    String getRolesByName(String key);

    <T> void insert(T t);

    <T> void remove(T t);

    IProject getProject(int id);

    IUser getUser(int id);

    IUser getUser(String email);

    ICategory getCategory(String id);

    ProjectState getProjectState(int id);

    Set<IUser> getAllUsers();

    Set<IUser> getUsersByCategoryAndRole(String idCategory, Class<? extends IRole> clazz);

    Set<IProject> getAllProjects();

    Set<IProject> getProjectsByCategory(String idCategory);

    Set<ICategory> getAllCategories();

    Set<Object> getRisorse();
}
