package it.unicam.cs.ids.DOIT.model;

public class Designer extends Role {
	public PartecipationRequest partecipationRequest;
	public RolesHandler rolesHandler;
	public Category category;

	public boolean createPartecipationRequest(Team team) {
		throw new UnsupportedOperationException();
	}
}