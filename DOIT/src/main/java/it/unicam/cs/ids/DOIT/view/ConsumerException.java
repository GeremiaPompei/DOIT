package it.unicam.cs.ids.DOIT.view;

@FunctionalInterface
public interface ConsumerException {
    void accept() throws Exception;
}
