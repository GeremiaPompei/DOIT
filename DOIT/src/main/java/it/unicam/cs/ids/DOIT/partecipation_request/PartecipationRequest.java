package it.unicam.cs.ids.DOIT.partecipation_request;

import it.unicam.cs.ids.DOIT.role.PendingRole;
import it.unicam.cs.ids.DOIT.role.Team;

import java.time.LocalDateTime;
import java.util.Objects;

public class PartecipationRequest {
	private boolean state;
	private String description;
	private LocalDateTime dateTime;
	private PendingRole role;
	private Team team;

	public PartecipationRequest(PendingRole role, Team team) {
		this.role = role;
		this.team = team;
		this.description = "Partecipation request sent...";
		this.state = false;
		this.dateTime = LocalDateTime.now();
	}

	public PendingRole getPendingRole() {
		return role;
	}

	public void displayed(String description) {
		this.state = true;
		this.description = description;
	}

	public Team getTeam() {
		return team;
	}

	public String getDescription() {
		return description;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
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