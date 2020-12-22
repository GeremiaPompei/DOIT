package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.Roles.DesignerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectProposerRole;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestoreRisorse {
    private final Map<Class, Set> risorse = new HashMap<>();

    public GestoreRisorse() {
        risorse.put(User.class, new HashSet<>());
        risorse.put(Project.class, new HashSet<>());
        risorse.put(Category.class, new HashSet<>());
        risorse.put(Class.class, new HashSet<>());
        risorse.get(Class.class).add(DesignerRole.class);
        risorse.get(Class.class).add(ProjectProposerRole.class);
        risorse.get(Class.class).add(ProgramManagerRole.class);
        risorse.get(Class.class).add(ProjectManagerRole.class);
        risorse.get(Category.class).add(new Category("Sport", "La mia CT."));
        risorse.get(Category.class).add(new Category("Informatica", "Indiani."));
        risorse.get(Category.class).add(new Category("Domotica", "In casa."));
    }

    public <T> T searchOne(Class<T> clazz ,Predicate<T> p) {
        return clazz.cast(risorse.get(clazz).stream().filter(p).findAny().orElse(null));
    }

    public <T> Set<T> search(Class<T> clazz ,Predicate<T> p) {
        return (Set<T>) risorse.get(clazz).stream().filter(p).collect(Collectors.toSet());
    }

    public Map<Class, Set> getRisorse() {
        return risorse;
    }
}
