package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String surname;
    private int birthYear;
    private String gender;
    private Set<Role> roles;

    public User(int id, String name, String surname, int birthYear, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public <T extends Role> boolean addRole(Class<T> clazz, Category category)
            throws ReflectiveOperationException {
        return this.roles.add(clazz.getConstructor(new Class<?>[]{User.class, Category.class}).newInstance(
                new Object[]{this, category}));
    }

    public <T extends Role> T getRole(Class<T> clazz) throws RoleException {
        return clazz.cast(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElseThrow(RoleException::new));
    }

    public <T extends Role> boolean removeRole(Class<T> clazz) {
        return this.roles.remove(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElse(null));
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + gender + '\'' +
                ", roles=" + roles +
                '}';
    }
}