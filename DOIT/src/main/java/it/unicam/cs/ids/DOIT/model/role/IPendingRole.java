package it.unicam.cs.ids.DOIT.model.role;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;

import java.util.Iterator;
import java.util.Set;

public interface IPendingRole {
    <T extends IPendingRole> PartecipationRequest<T> createPartecipationRequest(Project project);

    <T extends IPendingRole> Set<PartecipationRequest<T>> getMyPartecipationRequests();

    Set<Project> getProjectsByCategory(Iterator<Project> iterator, Category category);
}
