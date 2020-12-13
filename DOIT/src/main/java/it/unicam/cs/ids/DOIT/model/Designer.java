package it.unicam.cs.ids.DOIT.model;

public class Designer extends Role {
	private PartecipationRequest partecipationRequest;
	private RolesHandler rolesHandler;
	private Category category;
	private CurriculumVitae curriculumVitae;


	public Designer(User user, CurriculumVitae curriculumVitae) {
		super(user);
		this.curriculumVitae = curriculumVitae;
	}

	public boolean createPartecipationRequest(Team team) {
		throw new UnsupportedOperationException();
	}
}