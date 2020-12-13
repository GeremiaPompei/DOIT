package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager extends Role {
	public RolesHandler rolesHandler;
	public List<Team> teams = new ArrayList<>();

	public ProgramManager(User user) {
		super(user);
	}

	public boolean addDesigner() {
		throw new UnsupportedOperationException();
	}

	public boolean removeDesigner() {
		throw new UnsupportedOperationException();
	}

	public void setProjectManager(User user, Project project) {
		throw new UnsupportedOperationException();
	}

	public void initTeam(Project project) {
		Team team = new Team(super.getUser());
		this.teams.add(team);
		project.setTeam(team);
	}

	public void removePartecipationRequest(PartecipationRequest partecipationRequest) {
		throw new UnsupportedOperationException();
	}
}