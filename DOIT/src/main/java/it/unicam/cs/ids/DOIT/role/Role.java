package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.service.FactoryModel;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role {
    @Transient
    protected IFactoryModel factoryModel;

    @Id
    @Column(name = "ID_Role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ID_User")
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<Team> teams;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<Team> history;
    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL)
    private Set<Category> categories;

    public Role(User user, Category category, IFactoryModel factoryModel) {
        this.factoryModel = factoryModel;
        this.user = user;
        teams = new HashSet<>();
        categories = new HashSet<>();
        history = new HashSet<>();
        this.categories.add(category);
    }

    public User getUser() {
        return this.user;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void exitTeam(Team team) {
        Team teamFound = this.getInnerTeam(team);
        history.add(teamFound);
        teams.remove(teamFound);
    }

    public void enterTeam(Team team) {
        teams.add(team);
    }

    public Set<Team> getHistory() {
        return history;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        if (this.categories.size() == 1)
            throw new IllegalArgumentException("Non si puo eliminare una categoria quando ne rimane solo una!");
        this.teams.stream().filter(p -> p.getProject().getCategory().equals(category)).findAny().orElseThrow(
                () -> new IllegalArgumentException("Non puo essere eliminata una categoria in uso su uno dei progetti appartenenti al ruolo!"));
        this.categories.remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(user, role.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    protected Category getInnerCategory(Category category) {
        return this.getCategories().stream()
                .filter(c -> c.equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("L'utente non presenta la categoria!"));
    }

    protected PartecipationRequest getInnerDesignerRequest(DesignerRole designer, Team team) {
        PartecipationRequest pr = this.getTeams().stream()
                .filter(t -> t.equals(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + team.getId() + "]"))
                .getDesignerRequest().stream()
                .filter(t -> t.getPendingRole().getUser().getRolesHandler().isDesignerRole())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + designer.getUser().getId() + "]"));
        getInnerCategory(pr.getTeam().getProject().getCategory());
        return pr;
    }

    protected PartecipationRequest getInnerProgramManagerRequest(ProgramManagerRole programManager, Team team) {
        PartecipationRequest pr = this.getTeams().stream()
                .filter(t -> t.equals(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + team.getId() + "]"))
                .getProgramManagerRequest().stream()
                .filter(t -> t.getPendingRole().getUser().getRolesHandler().isProgramManager())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + programManager.getUser().getId() + "]"));
        getInnerCategory(pr.getTeam().getProject().getCategory());
        return pr;
    }

    protected Team getInnerTeam(Team team) {
        Team teamFound = this.getTeams().stream().filter(t -> t.getId().equals(team)).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il ruolo non ha il progetto con id: [" + team.getId() + "]"));
        getInnerCategory(teamFound.getProject().getCategory());
        return teamFound;
    }

    protected DesignerRole getInnerDesignerInTeam(DesignerRole designer, Team team) {
        return getInnerTeam(team).getDesigners().stream().filter(d -> d.getUser().getRolesHandler().isDesignerRole()).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il progetto: [" + team.getId() + "] non possiede il designer: [" + designer.getUser().getId() + "]"));
    }

    @Override
    public String toString() {
        return "Role{" +
                "team=" + teams.stream().map(t -> t.getId()).collect(Collectors.toSet()) +
                ", categories=" + categories +
                '}';
    }
}