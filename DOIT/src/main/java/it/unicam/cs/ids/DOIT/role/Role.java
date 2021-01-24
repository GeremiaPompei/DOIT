package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
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
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    @Id
    @Column(name = "ID_Role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_User")
    private User user;

    @Transient
    private Set<Team> teams;
    @Transient
    private Set<Team> history;
    @OneToMany(mappedBy = "name")
    private Set<Category> categories;

    public Role(User user, Category category) {
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

    public void exitTeam(Long idProject) {
        Team team = this.getInnerTeam(idProject);
        history.add(team);
        teams.remove(team);
    }

    public void enterTeam(Long idProject) {
        teams.add(servicesHandler.getResourceHandler().getProject(idProject).getTeam());
    }

    public Set<Team> getHistory() {
        return history;
    }

    public void addCategory(String idCategory) {
        Category category = servicesHandler.getResourceHandler().getCategory(idCategory);
        this.categories.add(category);
    }

    public void removeCategory(String idCategory) {
        Category category = servicesHandler.getResourceHandler().getCategory(idCategory);
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

    protected Category getInnerCategory(String idCategory) {
        return this.getCategories().stream()
                .filter(c -> c.getName().equalsIgnoreCase(idCategory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("L'utente non presenta la categoria!"));
    }

    protected PartecipationRequest getInnerDesignerRequest(Long idDesigner, Long idProject) {
        PartecipationRequest pr = this.getTeams().stream()
                .filter(t -> t.getProject().getId() == idProject)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + idProject + "]"))
                .getDesignerRequest().stream()
                .filter(t -> t.getPendingRole().getUser().getId() == idDesigner)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + idDesigner + "]"));
        getInnerCategory(pr.getTeam().getProject().getCategory().getName());
        return pr;
    }

    protected PartecipationRequest getInnerProgramManagerRequest(Long idDesigner, Long idProject) {
        PartecipationRequest pr = this.getTeams().stream()
                .filter(t -> t.getProject().getId() == idProject)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + idProject + "]"))
                .getProgramManagerRequest().stream()
                .filter(t -> t.getPendingRole().getUser().getId() == idDesigner)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + idDesigner + "]"));
        getInnerCategory(pr.getTeam().getProject().getCategory().getName());
        return pr;
    }

    protected Team getInnerTeam(Long idProject) {
        Team team = this.getTeams().stream().filter(t -> t.getId() == idProject).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il ruolo non ha il progetto con id: [" + idProject + "]"));
        getInnerCategory(team.getProject().getCategory().getName());
        return team;
    }

    protected DesignerRole getInnerDesignerInTeam(Long idDesigner, Long idProject) {
        return getInnerTeam(idProject).getDesigners().stream().filter(d -> d.getUser().getId() == idDesigner).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il progetto: [" + idProject + "] non possiede il designer: [" + idDesigner + "]"));
    }

    @Override
    public String toString() {
        return "Role{" +
                "team=" + teams.stream().map(t -> t.getId()).collect(Collectors.toSet()) +
                ", categories=" + categories +
                '}';
    }
}