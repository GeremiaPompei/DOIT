package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PartecipationRequest {
	private boolean state;
	private String description;
	private LocalDateTime date;
	private User designer;
	private Team team;

	public PartecipationRequest(User designer, Team team) {
		this.designer = designer;
		this.team = team;
		this.description = "Partecipation request sended...";
		this.state = false;
		this.date = LocalDateTime.now();
	}

	public User getDesigner() {
		return designer;
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

	@Override
	public String toString() {
		return "PartecipationRequest{" +
				"state=" + state +
				", description='" + description + '\'' +
				", date=" + date +
				", designer=" + designer.getId() +
				'}';
	}
}