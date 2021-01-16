package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.role.IPendingRole;
import it.unicam.cs.ids.DOIT.role.ITeam;

import java.time.LocalDateTime;

public class PartecipationRequest implements IPartecipationRequest {
	private boolean state;
	private String description;
	private LocalDateTime dateTime;
	private IPendingRole role;
	private ITeam team;

	public PartecipationRequest(IPendingRole role, ITeam team) {
		this.role = role;
		this.team = team;
		this.description = "Partecipation request sended...";
		this.state = false;
		this.dateTime = LocalDateTime.now();
	}

	public IPendingRole getPendingRole() {
		return role;
	}

	public void displayed(String description) {
		this.state = true;
		this.description = description;
	}

	public ITeam getTeam() {
		return team;
	}

	public String getDescription() {
		return description;
	}

	public boolean getState() {
		return state;
	}

	@Override
	public String toString() {
		return "PartecipationRequest{" +
				"state=" + state +
				", description='" + description + '\'' +
				", dateTime=" + dateTime +
				", role=" + role.getId() +
				", team=" + team.getProject().getId() +
				'}';
	}
}