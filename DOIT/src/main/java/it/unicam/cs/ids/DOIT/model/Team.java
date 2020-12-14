package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private boolean state = false;
	private int id;
	private Project project;
	private User programManager;
	private List<DesignerRole> designerRoles = new ArrayList<>();

	public Team(int id, Project project, User programManager) {
		this.id = id;
		this.project = project;
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

	public List<DesignerRole> getDesigners() {
		return designerRoles;
	}
}