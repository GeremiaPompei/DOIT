package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.role.RoleException;

import java.util.Set;

public interface IUser {
    int getId();

    String getName();

    String getSurname();

    String getBirthYear();

    String getSex();

    <T extends IRole> boolean addRole(Class<T> clazz, String idCategory)
            throws ReflectiveOperationException;

    <T extends IRole> T getRole(Class<T> clazz) throws RoleException;

    <T extends IRole> boolean removeRole(Class<T> clazz);

    Set<IRole> getRoles();

}
