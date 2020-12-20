package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String surname;
    private List<String> generalities;
    private Set<Role> roles;

    public User(int id, String name, String surname, List<String> generalities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.generalities = generalities;
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

    public List<String> getGeneralities() {
        return generalities;
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
                ", generalities=" + generalities +
                ", roles=" + roles +
                '}';
    }
}