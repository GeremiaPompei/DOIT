package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurriculumVitae {
    Map<Integer, List<String>> projects = new HashMap<>();

    public Map<Integer, List<String>> getProjects() {
        return projects;
    }

    public boolean enterProject(int idProject) {
        if (projects.isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(LocalDateTime.now() + "ENTER");
            projects.put(idProject, list);
            return true;
        } else if(!projects.get(idProject).get(projects.size() - 1).contains("ENTER")) {
            projects.get(idProject).add(LocalDateTime.now() + "ENTER");
            return true;
        }
        return false;
    }

    public boolean exitProject(int idProject) {
        if (!projects.isEmpty() && !projects.get(idProject).get(projects.size() - 1).contains("EXIT"))
            return projects.get(idProject).add(LocalDateTime.now() + "EXIT");
        return false;
    }
}