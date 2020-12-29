package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurriculumVitae {
    Map<LocalDateTime, String> history;
    Map<Integer, List<CVUnit>> projects;

    public CurriculumVitae() {
        this.history = new HashMap<>();
        this.projects = new HashMap<>();
    }

    public void enterProject(Project project) {
        if (projects.isEmpty()) {
            List<CVUnit> list = new ArrayList<>();
            list.add(new CVUnit(true));
            projects.put(project.getId(), list);
        } else if (!projects.get(project.getId()).get(projects.size() - 1).in)
            projects.get(project.getId()).add(new CVUnit(true));
    }

    public void exitProject(Project project) {
        if (!projects.isEmpty() && projects.get(project.getId()).get(projects.size() - 1).in)
            projects.get(project.getId()).add(new CVUnit(false));
    }

    public Map<Integer, List<CVUnit>> getProjects() {
        return projects;
    }

    public Map<LocalDateTime, String> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "CurriculumVitae{" +
                "history=" + history +
                ", projects=" + projects +
                '}';
    }

    private class CVUnit {
        private LocalDateTime dateTime;
        private boolean in;

        CVUnit(boolean in) {
            this.dateTime = LocalDateTime.now();
            this.in = in;
        }

        @Override
        public String toString() {
            return dateTime + " , " + in;
        }
    }
}