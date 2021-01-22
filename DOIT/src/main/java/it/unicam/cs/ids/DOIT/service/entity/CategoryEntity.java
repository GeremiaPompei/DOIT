package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.category.ICategory;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class CategoryEntity {
    //@Id
    private String name;
    private String description;

    public CategoryEntity(ICategory category) {
        name = category.getName();
        description = category.getDescription();
    }
}
