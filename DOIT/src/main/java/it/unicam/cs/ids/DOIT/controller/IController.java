package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;

public interface IController {
    void createUser(int id, String name, String surname, int birthdDay, String gender) throws Exception;

    void addRole(String roleName, String idCategory) throws Exception;

    void removeRole(String roleName) throws Exception;

    void addCategory(String roleName, String categoryName) throws Exception;

    void removeCategory(String roleName, String categoryName) throws Exception;

    void login(int id) throws Exception;

    void createProject(int id, String name, String description, String idCategory) throws Exception;

    void choosePgm(int idPgM, int idProject) throws Exception;

    Set<IUser> listPgm(String idCategory) throws Exception;

    void sendPr(int idDesigner) throws Exception;

    Set<IProject> listProjects(String idCategory) throws Exception;

    void removePr(int idD, int idP, String reason) throws Exception;

    void addDesigner(int idD, int idP) throws Exception;

    void choosePjm(int idD, int idP) throws Exception;

    Set<ITeam> listTeams() throws Exception;

    Set<IPartecipationRequest> listPR(int idProject) throws Exception;

    Set<IUser> listDesigner(int idDesigner) throws Exception;

    void upgradeState(int idProject) throws Exception;

    Set<String> getRoles();

    <T> Set<T> getRisorse(Class<T> t);

    IUser getUser();
}
