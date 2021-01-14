package it.unicam.cs.ids.DOIT.history;

import it.unicam.cs.ids.DOIT.project.IProject;

public interface IHistory {
    void enterProject(IProject project);
    void exitProject(IProject project);
}

