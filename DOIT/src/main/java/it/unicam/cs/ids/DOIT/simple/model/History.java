package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.IFactoryModel;
import it.unicam.cs.ids.DOIT.domain.model.IHistory;
import it.unicam.cs.ids.DOIT.domain.model.IHistoryUnit;
import it.unicam.cs.ids.DOIT.domain.model.IProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History implements IHistory {
    Map<IProject, List<IHistoryUnit>> projects;

    IFactoryModel factoryModel;

    public History(IFactoryModel factoryModel) {
        projects = new HashMap<>();
        this.factoryModel = factoryModel;
    }

    public void enterProject(IProject project) {
        if (projects.get(project) == null) {
            List<IHistoryUnit> list = new ArrayList<>();
            list.add(factoryModel.createHistoryUnit(true));
            projects.put(project, list);
        } else if (!projects.get(project).get(projects.size() - 1).getBool())
            projects.get(project).add(factoryModel.createHistoryUnit(true));
    }

    public void exitProject(IProject project) {
        if (projects.get(project) != null && projects.get(project).get(projects.size() - 1).getBool())
            projects.get(project).add(factoryModel.createHistoryUnit(false));
    }

    public Map<IProject, List<IHistoryUnit>> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return "Cronology{" +
                "projects=" + projects +
                '}';
    }
}
