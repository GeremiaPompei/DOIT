package it.unicam.cs.ids.DOIT.service.entity;

public interface ResourceEntity<T> {
    void fromObject(T t);
    T toObject() throws Exception;
}
