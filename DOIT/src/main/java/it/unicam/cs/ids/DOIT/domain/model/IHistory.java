package it.unicam.cs.ids.DOIT.domain.model;

public interface IHistory {
    void enterProject(IProject project);
    void exitProject(IProject project);
}

