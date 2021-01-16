package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.role.RoleException;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.util.HashSet;
import java.util.Set;

public class User implements IUser {
    private int id;
    private String name;
    private String surname;
    private String birthYear;
    private String sex;
    private Set<IRole> roles;

    public User(int id, String name, String surname, String birthYear, String sex) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.sex = sex;
        this.roles = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getSex() {
        return sex;
    }

    public <T extends IRole> boolean addRole(Class<T> clazz, String idCategory)
            throws ReflectiveOperationException {
        return this.roles.add(ServicesHandler.getInstance().getFactoryModel().createRole(clazz, this.getId(), idCategory));
    }

    public <T extends IRole> T getRole(Class<T> clazz) throws RoleException {
        return clazz.cast(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElseThrow(RoleException::new));
    }

    public <T extends IRole> boolean removeRole(Class<T> clazz) {
        IRole role = this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElse(null);
        if (!role.getTeams().isEmpty())
            throw new IllegalArgumentException("Bisogna non possedere alcun progetto nel ruolo da eliminare!");
        return this.roles.remove(role);
    }

    public Set<IRole> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + sex + '\'' +
                ", roles=" + roles +
                '}';
    }
}