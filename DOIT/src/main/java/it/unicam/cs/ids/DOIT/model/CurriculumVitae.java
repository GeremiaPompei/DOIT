package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurriculumVitae {
    Map<LocalDate, String> history;
    Map<Integer, List<CVUnit>> projects;

    public CurriculumVitae() {
        this.history = new HashMap<>();
        this.projects = new HashMap<>();
    }

    public boolean enterProject(int idProject) {
        if (projects.isEmpty()) {
            List<CVUnit> list = new ArrayList<>();
            list.add(new CVUnit(true));
            projects.put(idProject, list);
            return true;
        } else if (!projects.get(idProject).get(projects.size() - 1).in) {
            projects.get(idProject).add(new CVUnit(true));
            return true;
        }
        return false;
    }

    public boolean exitProject(int idProject) {
        if (!projects.isEmpty() && projects.get(idProject).get(projects.size() - 1).in)
            return projects.get(idProject).add(new CVUnit(false));
        return false;
    }

    public Map<Integer, List<CVUnit>> getProjects() {
        return projects;
    }

    public Map<LocalDate, String> getHistory() {
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