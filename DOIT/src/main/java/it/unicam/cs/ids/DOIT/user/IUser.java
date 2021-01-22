package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.role.RoleException;

import java.util.Set;

public interface IUser {
    Long getId();

    String getName();

    String getSurname();

    String getBirthDate();

    String getSex();

    String getEmail();

    String getPassword();

    TokenHandler getToken();

    <T extends IRole> boolean addRole(Class<T> clazz, String idCategory) throws ReflectiveOperationException;

    <T extends IRole> T getRole(Class<T> clazz) throws RoleException;

    <T extends IRole> boolean removeRole(Class<T> clazz) throws RoleException;

    <T extends IRole> boolean addRole(String idRole, String idCategory) throws ReflectiveOperationException;

    <T extends IRole> T getRole(String idRole) throws RoleException, ClassNotFoundException;

    <T extends IRole> boolean removeRole(String idRole) throws ClassNotFoundException, RoleException;

    Set<IRole> getRoles();

    void setToken(TokenHandler token);

}
