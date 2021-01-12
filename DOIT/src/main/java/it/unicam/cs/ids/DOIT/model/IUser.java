package it.unicam.cs.ids.DOIT.model;

import java.util.Set;

public interface IUser {
    int getId();

    String getName();

    String getSurname();

    int getBirthYear();

    String getSex();

    <T extends IRole> boolean addRole(Class<T> clazz, ICategory category, IFactoryModel factory)
            throws ReflectiveOperationException;

    <T extends IRole> T getRole(Class<T> clazz) throws RoleException;

    <T extends IRole> boolean removeRole(Class<T> clazz);

    Set<IRole> getRoles();

}
