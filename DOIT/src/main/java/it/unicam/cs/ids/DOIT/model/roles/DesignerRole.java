package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DesignerRole extends Role {

    private Set<PartecipationRequest> partecipationRequests;
    private CurriculumVitae curriculumVitae;


    public DesignerRole(User user, Category category) {
        super(user, category);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new CurriculumVitae();
    }

    public Set<PartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    public CurriculumVitae getCurriculumVitae() {
        return curriculumVitae;
    }

    public boolean createPartecipationRequest(Team team) {
        if (team.getPartecipationRequests().stream().map(p -> p.getDesigner()).collect(Collectors.toSet())
                .contains(this.getUser()))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this.getUser()))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" +
                    team.getProject().getCategory() + "]");
        PartecipationRequest partecipationRequest = new PartecipationRequest(this.getUser(), team);
        this.partecipationRequests.add(partecipationRequest);
        return team.getPartecipationRequests().add(partecipationRequest);
    }

    @Override
    public String toString() {
        return "DesignerRole{" +
                "role=" + super.toString() +
                ", partecipationRequests=" + partecipationRequests +
                ", curriculumVitae=" + curriculumVitae +
                '}';
    }
}