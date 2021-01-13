package it.unicam.cs.ids.DOIT.domain.storage;

import java.util.Set;
import java.util.function.Predicate;

public interface IResourceHandler {
    <T>void insert(T t);
    <T>void remove(T t);
    <T> T searchOne(Class<T> clazz, Predicate<T> p);
    <T> Set<T> search(Class<T> clazz, Predicate<T> p);

}
