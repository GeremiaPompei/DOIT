package it.unicam.cs.ids.DOIT.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    abstract PartecipationRequest createPartecipationRequest(Project project);

    abstract Set<PartecipationRequest> getMyPartecipationRequests();

    abstract Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category);
}
