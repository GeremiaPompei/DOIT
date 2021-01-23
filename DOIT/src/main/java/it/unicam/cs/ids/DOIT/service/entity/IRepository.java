package it.unicam.cs.ids.DOIT.service.entity;

import java.util.List;

public interface IRepository<T, R> {
    void save(T t);
    void delete(R r);
    void update(T t);
    T findById(R r);
    List<T> findAll();
}
