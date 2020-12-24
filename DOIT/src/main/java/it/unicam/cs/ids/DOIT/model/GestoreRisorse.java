package it.unicam.cs.ids.DOIT.model;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestoreRisorse {
    private final Map<Class, Set> risorse = new HashMap<>();

    public GestoreRisorse() {
        risorse.put(User.class, new HashSet<>());
        risorse.put(Project.class, new HashSet<>());
        risorse.put(Category.class, new HashSet<>());
        risorse.put(String.class, new HashSet<>());
        Arrays.stream(new File("src/main/java/it/unicam/cs/ids/DOIT/model/roles").list())
                .forEach(s -> risorse.get(String.class).add(s.replace(".java", "")));
        risorse.get(Category.class).add(new Category("Sport", "La mia CT."));
        risorse.get(Category.class).add(new Category("Informatica", "Indiani."));
        risorse.get(Category.class).add(new Category("Domotica", "In casa."));
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
}
