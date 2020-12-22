package it.unicam.cs.ids.DOIT.view;

@FunctionalInterface
public interface PredicateException<T> {
    boolean apply(T t) throws Exception;
}
