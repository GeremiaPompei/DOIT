package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;

public interface IResourceHandler {
    <T> void insert(T t);

    <T> void remove(T t);

    IProject getProject(int id);

    Set<IProject> getProjectsByCategory(String idCategory);

    ProjectState getProjectState(int id);

    IUser getUser(int id);

    Set<IUser> getAllUser();

    Set<IUser> getUsersByCategoryAndRole(String idCategory, Class<? extends IRole> clazz);

    ICategory getCategory(String id);

    IPartecipationRequest getPR(int idDesigner, int idProject);

    Set<IProject> getAllProjects();

    Set<ICategory> getAllCategories();
}
