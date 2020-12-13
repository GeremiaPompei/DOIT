package it.unicam.cs.ids.DOIT.model;

public class ProjectManager extends Role {
	public ProjectState projectState;
	public RolesHandler rolesHandler;

	public ProjectManager(User user) {
		super(user);
	}

	public boolean changeState(ProjectState state) {
		throw new UnsupportedOperationException();
	}
}