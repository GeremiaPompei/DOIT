package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.Set;
import java.util.function.Predicate;

public interface IDesignerRole extends IRole {

    Set<IPartecipationRequest> getPartecipationRequests();
    IPartecipationRequest createPartecipationRequest(ITeam team, IFactoryModel factory);
    Predicate<IProject> getProjects(ICategory category);
}
