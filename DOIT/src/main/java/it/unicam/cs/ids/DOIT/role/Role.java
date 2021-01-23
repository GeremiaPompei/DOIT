package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role implements IRole {

    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    private IUser user;

    private Set<ITeam> teams;

    private Set<ITeam> history;

    private Set<ICategory> categories;

    public Role(IUser user, ICategory category) {
        this.user = user;
        teams = new HashSet<>();
        categories = new HashSet<>();
        history = new HashSet<>();
        this.categories.add(category);
    }

    public IUser getUser() {
        return this.user;
    }

    public Set<ITeam> getTeams() {
        return teams;
    }

    public Set<ICategory> getCategories() {
        return categories;
    }

    @Override
    public void exitTeam(Long idProject) {
        ITeam team = this.getInnerTeam(idProject);
        history.add(team);
        teams.remove(team);
    }

    @Override
    public void enterTeam(Long idProject) {
        teams.add(servicesHandler.getResourceHandler().getProject(idProject).getTeam());
    }

    public Set<ITeam> getHistory() {
        return history;
    }

    @Override
    public void addCategory(String idCategory) {
        ICategory category = servicesHandler.getResourceHandler().getCategory(idCategory);
        this.categories.add(category);
    }

    @Override
    public void removeCategory(String idCategory) {
        ICategory category = servicesHandler.getResourceHandler().getCategory(idCategory);
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

    protected ICategory getInnerCategory(String idCategory) {
        return this.getCategories().stream()
                .filter(c -> c.getName().equalsIgnoreCase(idCategory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("L'utente non presenta la categoria!"));
    }

    protected IPartecipationRequest getInnerDesignerRequest(Long idDesigner, Long idProject) {
        IPartecipationRequest pr = this.getTeams().stream()
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

    protected IPartecipationRequest getInnerProgramManagerRequest(Long idDesigner, Long idProject) {
        IPartecipationRequest pr = this.getTeams().stream()
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

    protected ITeam getInnerTeam(Long idProject) {
        ITeam team = this.getTeams().stream().filter(t -> t.getId() == idProject).findAny().orElseThrow(() ->
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