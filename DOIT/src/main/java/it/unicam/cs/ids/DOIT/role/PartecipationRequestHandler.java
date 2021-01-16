package it.unicam.cs.ids.DOIT.role;

public interface PartecipationRequestHandler {
    void acceptPR(int idDesigner, int idProject) throws RoleException;

    void removePR(int idDesigner, int idProject, String description);
}
