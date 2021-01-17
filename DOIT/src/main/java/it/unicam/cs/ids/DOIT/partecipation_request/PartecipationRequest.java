package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.role.IPendingRole;
import it.unicam.cs.ids.DOIT.role.ITeam;

import java.time.LocalDateTime;
import java.util.Objects;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PartecipationRequest that = (PartecipationRequest) o;
		return Objects.equals(role, that.role) && Objects.equals(team, that.team);
	}

	@Override
	public int hashCode() {
		return Objects.hash(role, team);
	}

	@Override
	public String toString() {
		return "PartecipationRequest{" +
				"state=" + state +
				", description='" + description + '\'' +
				", dateTime=" + dateTime +
				", role=" + role.getUser().getId() +
				", team=" + team.getProject().getId() +
				'}';
	}
}