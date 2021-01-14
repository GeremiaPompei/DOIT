package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.history.IHistory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;

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

    Set<IUser> listDesignerForProgramManager(int idDesigner) throws Exception;

    Set<IUser> listDesignerForPrjManager(int idDesigner) throws Exception;

     void upgradeState(int idProject) throws Exception;

    void downgradeState(int idProject) throws  Exception;

    String visualizeState(int idProject);

    Set<Class<? extends IRole>> getChoosableRoles();

    <T> Set<T> getRisorse(Class<T> t);

    IUser getUser();

     void exitAll(int idProject) throws Exception;

    void insertEvaluation(int idDesigner, int idProject, int evaluation) throws Exception;

    IHistory visualizeHistory(String role) throws RoleException;

    Set<IProject> listProjectsOwnedByPrjManager() throws Exception;

    Integer getEvaluation(int idProject) throws Exception;

    void removeDesigner(int idD, int idT) throws Exception;

    void openRegistrations(int idT) throws Exception;

    void closeRegistrations(int idT) throws Exception;
}
