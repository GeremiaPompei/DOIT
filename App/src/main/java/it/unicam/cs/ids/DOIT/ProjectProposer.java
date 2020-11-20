package it.unicam.cs.ids.DOIT;

import java.util.List;

public class ProjectProposer extends User implements IProjectProposer {
    public ProjectProposer(int id, String name, String surname, List<String> generalities, List<IProject> projects) {
        super(id, name, surname, generalities, projects);
    }

    public IProject createProject(int id, String name, String description) {
        return null;
    }
}
