package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.IProjectState;

public class ProjectState implements IProjectState {

	private int id;
	private String name;
	private String description;

	public ProjectState(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ProjectState{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}