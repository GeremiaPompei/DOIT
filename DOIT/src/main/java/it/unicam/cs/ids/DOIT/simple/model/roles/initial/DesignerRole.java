package it.unicam.cs.ids.DOIT.simple.model.roles.initial;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.domain.model.roles.IDesignerRole;
import it.unicam.cs.ids.DOIT.simple.model.Role;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DesignerRole extends Role implements IDesignerRole {

    private Set<IPartecipationRequest> partecipationRequests;
    //TODO aggiungere informazioni del designer su esperienze pregresse.
    private Map<LocalDate, String> curriculumVitae;
    private Map<IProject, Integer> evaluations;


    public DesignerRole(IUser user, ICategory category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
        this.partecipationRequests = new HashSet<>();
        this.curriculumVitae = new HashMap<>();
    }

    public Set<IPartecipationRequest> getPartecipationRequests() {
        return partecipationRequests;
    }

    public IPartecipationRequest createPartecipationRequest(ITeam team, IFactoryModel factory) {
        if (team.getPartecipationRequests().stream().map(p -> p.getUser()).collect(Collectors.toSet())
                .contains(this.getUser()))
            throw new IllegalArgumentException("Partecipation request gia presente nel team!");
        if (team.getDesigners().contains(this.getUser()))
            throw new IllegalArgumentException("Designer gia presente nel team!");
        if (!this.getCategories().contains(team.getProject().getCategory()))
            throw new IllegalArgumentException("L'utente non presenta la categoria: [" +
                    team.getProject().getCategory() + "]");
        IPartecipationRequest partecipationRequest = factory.createPartecipationRequest(this.getUser(), team);
        this.partecipationRequests.add(partecipationRequest);
        team.getPartecipationRequests().add(partecipationRequest);
        return partecipationRequest;
    }

    public Predicate<IProject> getProjects(ICategory category) {
        return p -> p.getCategory().equals(category);
    }

    @Override
    public void enterEvaluation(IProject project, int evaluation) {
        this.evaluations.put(project, evaluation);
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