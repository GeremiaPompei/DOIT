package it.unicam.cs.ids.DOIT.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartecipationRequest {
	private boolean state;
	private String description;
	private LocalDate date;
	private User user;
	public Team team;
	public List<Designer> _invia = new ArrayList<>();

	public void addPartecipationRequest(PartecipationRequest partecipationRequest) {
		throw new UnsupportedOperationException();
	}

	public void setDescription(String description) {
		throw new UnsupportedOperationException();
	}
}