package it.unicam.cs.ids.DOIT.model;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestoreRisorse {
    private static GestoreRisorse instance;

    public static GestoreRisorse getInstance() {
        if (instance == null)
            instance = new GestoreRisorse();
        return instance;
    }

    public void clear() {
        instance = null;
    }

    private final Map<Class, Set> risorse = new HashMap<>();
    private Set<String> roles;

    private GestoreRisorse() {
        risorse.put(User.class, new HashSet<>());
        risorse.put(Project.class, new HashSet<>());
        risorse.put(Category.class, new HashSet<>());
        roles = new HashSet<>();
        Arrays.stream(new File("src/main/java/it/unicam/cs/ids/DOIT/model/roles/initial").list())
                .forEach(s -> roles.add(s.replace(".java", "")));
    }

    public <T> T searchOne(Class<T> clazz, Predicate<T> p) {
        return clazz.cast(risorse.get(clazz).stream().filter(p).findAny().orElse(null));
    }

    public <T> Set<T> search(Class<T> clazz, Predicate<T> p) {
        return (Set<T>) risorse.get(clazz).stream().filter(p).collect(Collectors.toSet());
    }

    public Map<Class, Set> getRisorse() {
        return risorse;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
