package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.user.User;

import java.util.Iterator;
import java.util.Set;

public abstract class PendingRole extends Role {
    public PendingRole() {
    }

    public PendingRole(User user, Category category) {
        super(user, category);
    }

    abstract <T extends PendingRole> PartecipationRequest<T> createPartecipationRequest(Project project);

    abstract <T extends PendingRole> Set<PartecipationRequest<T>> getMyPartecipationRequests();

    abstract Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category);
}
