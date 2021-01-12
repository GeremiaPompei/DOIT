package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.Set;

public class User implements IUser {
    private int id;
    private String name;
    private String surname;
    private int birthYear;
    private String sex;
    private Set<IRole> roles;

    public User(int id, String name, String surname, int birthYear, String sex) {
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

    public int getBirthYear() {
        return birthYear;
    }

    public String getSex() {
        return sex;
    }

    public <T extends IRole> boolean addRole(Class<T> clazz, ICategory category, IFactoryModel factory)
            throws ReflectiveOperationException {
        return this.roles.add(factory.createRole(clazz, this, category));
    }

    public <T extends IRole> T getRole(Class<T> clazz) throws RoleException {
        return clazz.cast(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElseThrow(RoleException::new));
    }

    public <T extends IRole> boolean removeRole(Class<T> clazz) {
        return this.roles.remove(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElse(null));
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