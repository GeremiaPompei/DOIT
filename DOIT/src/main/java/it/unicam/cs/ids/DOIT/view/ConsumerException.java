package it.unicam.cs.ids.DOIT.view;

@FunctionalInterface
public interface ConsumerException<T> {
    void accept(T t) throws Exception;
}
