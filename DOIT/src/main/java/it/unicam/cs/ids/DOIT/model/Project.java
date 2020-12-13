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

	public Project(int id, String name, String description, ProjectProposer projectProposer, Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.projectProposer = projectProposer;
		this.category = category;
		//this.projectState = INITIAL;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean checkProjectManager(User projectManager) {
		throw new UnsupportedOperationException();
	}

	public Team getTeam() {
		return team;
	}
}