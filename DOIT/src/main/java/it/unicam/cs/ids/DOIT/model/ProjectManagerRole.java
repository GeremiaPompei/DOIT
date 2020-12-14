package it.unicam.cs.ids.DOIT.model;

public class ProjectManagerRole extends Role {
	public ProjectState projectState;

	public ProjectManagerRole(User user) {
		super(user);
	}

	public boolean changeState(ProjectState state) {
		throw new UnsupportedOperationException();
	}
}