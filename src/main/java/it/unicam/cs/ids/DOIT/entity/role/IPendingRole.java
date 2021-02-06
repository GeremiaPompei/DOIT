package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;

import java.util.Iterator;
import java.util.Set;

public interface IPendingRole <T extends IPendingRole>  extends ISubscriber {
    PartecipationRequest<T> createPartecipationRequest(Project project);

    Set<PartecipationRequest<T>> myPartecipationRequests();

    void removeMyPr(PartecipationRequest<T> pr);

    Set<Project> projectsByCategory(Iterator<Project> iterator, Category category);
}
