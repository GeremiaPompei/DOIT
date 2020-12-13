package it.unicam.cs.ids.DOIT.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User extends Searcher {
    private int id;
    private String name;
    private String surname;
    private List<String> generalities;
    private RolesHandler rolesHandler;

    public User(int id, String name, String surname, List<String> generalities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.generalities = generalities;
        this.rolesHandler = new RolesHandler();
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

    public RolesHandler getRolesHandler() {
        return rolesHandler;
    }

    //TODO

    private Set<Role> roles = new HashSet<>();

    public <T extends Role> void addRole(Class<T> clas, Object[] params, Class<?>... classes)
            throws ReflectiveOperationException {
        this.roles.add(clas.getConstructor(classes).newInstance(params));
    }

    public <T extends Role> T getRole(Class<T> clas) throws RoleException {
        return clas.cast(this.roles
                .stream()
                .filter(clas::isInstance)
                .findAny()
                .orElseThrow(RoleException::new));
    }
}