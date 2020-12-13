package it.unicam.cs.ids.DOIT.model;

public class Designer extends Role {
	public PartecipationRequest partecipationRequest;
	public RolesHandler rolesHandler;
	public Category category;

	public Designer(User user) {
		super(user);
	}

	public boolean createPartecipationRequest(Team team) {
		throw new UnsupportedOperationException();
	}
}