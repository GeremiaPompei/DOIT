package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface IDesignerRole extends IRole {

    Set<IPartecipationRequest> getPartecipationRequests();
    IPartecipationRequest createPartecipationRequest(ITeam team, IFactoryModel factory);
    Predicate<IProject> getProjects(ICategory category);
    void enterEvaluation(IProject project, int evaluation);
    Map<IProject, Integer> getEvaluations();
}
