package it.unicam.cs.ids.DOIT;

import java.util.List;

public class ProjectManager extends User implements IProjectManager, IDesigner {

    public ProjectManager(int id, String name, String surname, List<String> generalities, List<IProject> projects) {
        super(id, name, surname, generalities, projects);
    }

    public void partecipationRequest() {

    }

    public void upgradeProjectState() {

    }

}
