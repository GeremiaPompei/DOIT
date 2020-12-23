package it.unicam.cs.ids.DOIT.model.Roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.ArrayList;
import java.util.List;

public class DesignerRole extends Role {

    private List<PartecipationRequest> partecipationRequests;
    private CurriculumVitae curriculumVitae;


    public DesignerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new ArrayList<>();
        this.curriculumVitae = new CurriculumVitae();
    }

    public List<PartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    public CurriculumVitae getCurriculumVitae() {
        return curriculumVitae;
    }

    public boolean createPartecipationRequest(Team team) {
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" +
                    team.getProject().getCategory() + "]");
        PartecipationRequest partecipationRequest = new PartecipationRequest(this.getUser(), team);
        this.partecipationRequests.add(partecipationRequest);
        return team.getPartecipationRequests().add(partecipationRequest);
    }

}