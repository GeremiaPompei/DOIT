package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.Iterator;
import java.util.Set;

public abstract class PendingRole extends Role {
    public PendingRole(User user, Category category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
    }

    abstract void createPartecipationRequest(Team team);

    abstract Set<PartecipationRequest> getMyPartecipationRequests();

    abstract Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category);
}
