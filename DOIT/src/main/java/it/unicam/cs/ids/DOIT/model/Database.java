package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    public final static Set<User> users = new HashSet<>();
    public final static Set<Project> projects = new HashSet<>();
    public final static Set<Category> categories = new HashSet<>();
    public final static Set<String> roles = new HashSet<>();

    static {
        roles.add(DesignerRole.class.getSimpleName());
        roles.add(ProjectProposerRole.class.getSimpleName());
        roles.add(ProgramManagerRole.class.getSimpleName());
        roles.add(ProjectManagerRole.class.getSimpleName());
        categories.add(new Category("Sport", "La mia CT."));
        categories.add(new Category("Informatica", "Indiani."));
        categories.add(new Category("Domotica", "In casa."));
    }

    public static <T extends Role> Set<User> searchUser(Class<T> clazz, Category category) {
        return searchUser(clazz, u -> u.getCategories().contains(category));
    }

    public static <T extends Role> Set<User> searchUser(Class<T> clazz) {
        return searchUser(clazz, u -> true);
    }

    public static <T extends Role> Set<User> searchUser(Class<T> clazz, String key) {
        return searchUser(clazz, u -> isContained(key, u.getUser()));
    }

    private static <T extends Role> Set<User> searchUser(Class<T> clazz, Predicate<T> predicate) {
        Set<Role> foundRoles = users.stream().map(u -> {
            try {
                return u.getRole(clazz);
            } catch (RoleException e) {
                return null;
            }
        }).collect(Collectors.toSet());
        foundRoles.remove(null);
        return foundRoles.stream().filter(p -> predicate.test(clazz.cast(p))).map(u -> u.getUser())
                .collect(Collectors.toSet());
    }

    public static <T extends Role> Set<Project> searchProject(Category category) {
        return projects.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toSet());
    }

    public static User searchUser(int id) {
        return users.stream()
                .filter((u) -> u.getId() == id)
                .findAny()
                .orElse(null);
    }

    public static Project searchProject(int id) {
        return projects.stream()
                .filter((p) -> p.getId() == id)
                .findAny()
                .orElse(null);
    }

    public static Category searchCategory(String name) {
        return categories.stream()
                .filter((c) -> c.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    public static Class<? extends Role> searchRole(String name) {
        try {
            return (Class<? extends Role>) Class.forName("it.unicam.cs.ids.DOIT.model." + roles.stream()
                    .filter((r) -> r.equalsIgnoreCase(name))
                    .findAny()
                    .orElse(null));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static boolean isContained(String str, User user) {
        return Integer.toString(user.getId()).contains(str) ||
                user.getName().contains(str) ||
                user.getSurname().contains(str) ||
                user.getGeneralities().stream().anyMatch(x -> x.contains(str));
    }

}
