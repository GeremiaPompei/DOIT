package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartecipationRequest {
	private boolean state;
	private String description;
	private LocalDate date;
	private User user;
	private Team team;

	public PartecipationRequest(User user, Team team) {
		this.user = user;
		this.team = team;
		this.description = "Partecipation request sended...";
		this.state = false;
		this.date = LocalDate.now();
	}

	public User getUser() {
		return user;
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
}