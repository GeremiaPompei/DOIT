package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private boolean state;
	private int id;
	private Project project;
	private User programManager;
	private List<Designer> designers = new ArrayList<>();

	public Team(User programManager) {
		this.programManager = programManager;
	}

	public boolean isState() {
		return state;
	}

	public int getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}

	public User getProgramManager() {
		return programManager;
	}

	public List<Designer> getDesigners() {
		return designers;
	}
}