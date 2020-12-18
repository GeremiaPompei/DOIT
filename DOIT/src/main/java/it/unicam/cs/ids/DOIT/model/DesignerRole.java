package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class DesignerRole extends Role {

	private List<PartecipationRequest> partecipationRequests = new ArrayList<>();
	private Category category;
	private CurriculumVitae curriculumVitae;


	public DesignerRole(User user, Category category, CurriculumVitae curriculumVitae) {
		super(user, category);
		this.curriculumVitae = curriculumVitae;
	}

	public boolean createPartecipationRequest(Team team) {
		PartecipationRequest pr = new PartecipationRequest(this.getUser(), team);
		boolean b = this.partecipationRequests.add(pr);
		return team.getPartecipationRequests().add(pr) && b;
	}

	public List<PartecipationRequest> getPartecipationRequests() {
		return partecipationRequests;
	}

	public CurriculumVitae getCurriculumVitae() {
		return curriculumVitae;
	}
}