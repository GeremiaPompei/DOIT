package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager extends Role {
	public RolesHandler rolesHandler;
	public List<Team> teams = new ArrayList<>();

	public boolean addDesigner() {
		throw new UnsupportedOperationException();
	}

	public boolean removeDesigner() {
		throw new UnsupportedOperationException();
	}

	public void setProjectManager(User user, Project project) {
		throw new UnsupportedOperationException();
	}

	public void initTeam(User user) {
		throw new UnsupportedOperationException();
	}

	public void removePartecipationRequest(PartecipationRequest partecipationRequest) {
		throw new UnsupportedOperationException();
	}
}