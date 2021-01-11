package it.unicam.cs.ids.DOIT.model;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourceHandler implements IResourceHandler{

    private final Map<Class, Set> risorse = new HashMap<>();
    private Set<String> roles;

    public ResourceHandler() {
        risorse.put(User.class, new HashSet<>());
        risorse.put(Project.class, new HashSet<>());
        risorse.put(Category.class, new HashSet<>());
        risorse.put(ProjectState.class, new HashSet());
        roles = new HashSet<>();
        Arrays.stream(new File("src/main/java/it/unicam/cs/ids/DOIT/model/roles/initial").list())
                .forEach(s -> roles.add(s.replace(".java", "")));
    }

    @Override
    public <T> T searchOne(Class<T> clazz, Predicate<T> p) {
        return clazz.cast(risorse.get(clazz).stream().filter(p).findAny().orElse(null));
    }

    @Override
    public <T> Set<T> search(Class<T> clazz, Predicate<T> p) {
        return (Set<T>) risorse.get(clazz).stream().filter(p).collect(Collectors.toSet());
    }

    @Override
    public Map<Class, Set> getRisorse() {
        return risorse;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public <T> void insert(T t) {
        this.risorse.get(t.getClass()).add(t);
    }

    @Override
    public <T> void remove(T t) {
        this.risorse.get(t.getClass()).remove(t);
    }
}
