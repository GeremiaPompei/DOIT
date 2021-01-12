package it.unicam.cs.ids.DOIT.storage;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourceHandler implements IResourceHandler {

    private final Set<Object> risorse;

    ResourceHandler() {
        risorse = new HashSet<>();
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
    public <T> void insert(T t) {
        this.risorse.add(t);
    }

    @Override
    public <T> void remove(T t) {
        this.risorse.remove(t);
    }
}
