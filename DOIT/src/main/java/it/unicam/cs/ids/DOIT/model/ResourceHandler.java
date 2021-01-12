package it.unicam.cs.ids.DOIT.model;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourceHandler implements IResourceHandler {

    private final Set<Object> risorse;
    private Set<String> roles;

    public ResourceHandler() {
        risorse = new HashSet<>();
        roles = new HashSet<>();
        Arrays.stream(new File("src/main/java/it/unicam/cs/ids/DOIT/model/roles/initial").list())
                .forEach(s -> roles.add(s.replace(".java", "")));
    }

    @Override
    public <T> T searchOne(Class<T> clazz, Predicate<T> p) {
        return clazz.cast(risorse.stream().filter(clazz::isInstance).map(clazz::cast).filter(p)
                .findAny().orElse(null));
    }

    @Override
    public <T> Set<T> search(Class<T> clazz, Predicate<T> p) {
        return risorse.stream().filter(clazz::isInstance).map(clazz::cast)
                .filter(p).collect(Collectors.toSet());
    }

    @Override
    public Set<Object> getRisorse() {
        return risorse;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public <T> void insert(T t) {
        this.risorse.add(t);
    }

    @Override
    public <T> void remove(T t) {
        this.risorse.remove(t);
    }
}
