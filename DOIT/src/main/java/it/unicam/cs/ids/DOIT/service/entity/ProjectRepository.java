package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.project.IProject;

import java.util.List;

public class ProjectRepository implements IRepository<IProject, Long> {
    @Override
    public void save(IProject project) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(IProject project) {

    }

    @Override
    public IProject findById(Long aLong) {
        return null;
    }

    @Override
    public List<IProject> findAll() {
        return null;
    }
}

