package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectProposerRole extends Role {
	//public List<Project> crea = new ArrayList<>();

	public ProjectProposerRole(User user) {
		super(user);
	}

	public void createProject(int id, String name, String description, Category category) {
		super.getProject().add(new Project(id, name, description, this.getUser(), category));
	}
}