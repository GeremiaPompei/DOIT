package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.category.ICategory;

import java.util.List;

public class CategoryRepository implements IRepository<ICategory, String> {
    @Override
    public void save(ICategory category) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(ICategory category) {

    }

    @Override
    public ICategory findById(String s) {
        return null;
    }

    @Override
    public List<ICategory> findAll() {
        return null;
    }
}
