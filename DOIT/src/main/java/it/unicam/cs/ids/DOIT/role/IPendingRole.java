package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.project.IProject;

import java.util.Set;

public interface IPendingRole extends IRole {
    void createPartecipationRequest(Long idProject);

    Set<IPartecipationRequest> getMyPartecipationRequests();

    Set<IProject> getProjectsByCategory(String idCategory);
}
