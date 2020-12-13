package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectProposer extends Role {
	public RolesHandler rolesHandler;
	public List<Project> crea = new ArrayList<>();

	public ProjectProposer(User user) {
		super(user);
	}

	public Project createProject(int id, String name, String description, Category category) {
		throw new UnsupportedOperationException();
	}
}