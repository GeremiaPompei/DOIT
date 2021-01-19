package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.role.RoleException;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface IUserHandler {
    void logIn(int id);

    void signIn(String name, String surname, String birthDate, String sex);

    void logOut();

    IUser getUser();

    void addRole(String roleName, String idCategory) throws Exception;

    void removeRole(String roleName) throws RoleException;

    Set<Class<? extends IRole>> getChoosableRoles();

    Class<? extends IRole> getRole(String roleName);
}
