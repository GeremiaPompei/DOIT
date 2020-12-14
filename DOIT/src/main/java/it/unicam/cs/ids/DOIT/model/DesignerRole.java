package it.unicam.cs.ids.DOIT.model;

public class DesignerRole extends Role {
	private PartecipationRequest partecipationRequest;
	private Category category;
	private CurriculumVitae curriculumVitae;


	public DesignerRole(User user, CurriculumVitae curriculumVitae) {
		super(user);
		this.curriculumVitae = curriculumVitae;
	}

	public boolean createPartecipationRequest(Team team) {
		throw new UnsupportedOperationException();
	}
}