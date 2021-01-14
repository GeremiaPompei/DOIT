package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.role.IUser;

import java.time.LocalDateTime;

public class PartecipationRequest implements IPartecipationRequest {
	private boolean state;
	private String description;
	private LocalDateTime dateTime;
	private IUser designer;
	private ITeam team;

	public PartecipationRequest(IUser designer, ITeam team) {
		this.designer = designer;
		this.team = team;
		this.description = "Partecipation request sended...";
		this.state = false;
		this.dateTime = LocalDateTime.now();
	}

	public IUser getUser() {
		return designer;
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
				", date=" + dateTime +
				", designer=" + designer.getId() +
				'}';
	}
}