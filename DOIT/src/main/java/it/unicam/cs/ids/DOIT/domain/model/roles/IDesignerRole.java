package it.unicam.cs.ids.DOIT.domain.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;

import java.util.Set;
import java.util.function.Predicate;

public interface IDesignerRole extends IRole {

    Set<IPartecipationRequest> getPartecipationRequests();
    IPartecipationRequest createPartecipationRequest(ITeam team, IFactoryModel factory);
    Predicate<IProject> getProjects(ICategory category);
    void enterEvaluation(IProject project, int evaluation);
}
