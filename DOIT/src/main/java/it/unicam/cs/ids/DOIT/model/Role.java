package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Role {

	private User user;

	private List<Project> project = new ArrayList<>();

	public Role(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public List<Project> getProject() {
		return project;
	}
}