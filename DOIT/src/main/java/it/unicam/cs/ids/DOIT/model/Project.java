package it.unicam.cs.ids.DOIT.model;

public class Project {
	private int id;
	private String name;
	private String description;
	private ProjectProposer projectProposer;
	private Category category;
	private ProjectState projectState;
	private Team team;

	public void setProjectManager(User projectManager) {
		throw new UnsupportedOperationException();
	}

	public Project(int id, String name, String description) {
		throw new UnsupportedOperationException();
	}

	public void setTeam(Team team) {
		throw new UnsupportedOperationException();
	}

	public boolean checkProjectManager(User projectManager) {
		throw new UnsupportedOperationException();
	}
}