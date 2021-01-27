package it.unicam.cs.ids.DOIT.model.role;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role {

    @Id
    @Column(name = "ID_Role")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idUser;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Project> projects;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Project> history;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> categories;

    public Role() {
    }

    public Role(User user, Category category) {
        this.idUser = user.getId();
        projects = new HashSet<>();
        categories = new HashSet<>();
        history = new HashSet<>();
        this.categories.add(category);
    }

    public abstract String getType();

    public Long getIdUser() {
        return this.idUser;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void exitProject(Project projectInput) {
        Project project = this.getInnerProject(projectInput);
        history.add(project);
        projects.remove(project);
    }

    public void enterProject(Project project) {
        projects.add(project);
    }

    public Set<Project> getHistory() {
        return history;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        if (this.categories.size() == 1)
            throw new IllegalArgumentException("Non si puo eliminare una categoria quando ne rimane solo una!");
        if (this.projects.stream().filter(p -> p.getCategory().equals(category)).findAny().orElse(null) != null)
            throw new IllegalArgumentException("Non puo essere eliminata una categoria in uso su uno dei progetti appartenenti al ruolo!");
        this.categories.remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(idUser, role.getIdUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }

    protected Category getInnerCategory(Category category) {
        return this.getCategories().stream()
                .filter(c -> c.equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("L'utente non presenta la categoria!"));
    }

    protected PartecipationRequest getInnerDesignerRequest(DesignerRole designer, Project projectInput) {
        PartecipationRequest pr = this.getProjects().stream()
                .filter(p -> p.equals(projectInput))
                .map(p -> p.getTeam())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + projectInput.getId() + "]"))
                .getDesignerRequest().stream()
                .filter(t -> t.getPendingRole().getIdUser().equals(designer.getIdUser()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + designer.getIdUser() + "]"));
        getInnerCategory(projectInput.getCategory());
        return pr;
    }

    protected PartecipationRequest getInnerProgramManagerRequest(ProgramManagerRole programManager, Project projectInput) {
        PartecipationRequest pr = this.getProjects().stream()
                .filter(p -> p.equals(projectInput))
                .map(p -> p.getTeam())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non sei in possesso del team con id: [" + projectInput.getId() + "]"))
                .getProgramManagerRequest().stream()
                .filter(t -> t.getPendingRole().getIdUser().equals(programManager.getIdUser()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Non vi è una request fatta dal ruolo con id: [" + programManager.getIdUser() + "]"));
        getInnerCategory(projectInput.getCategory());
        return pr;
    }

    protected Project getInnerProject(Project projectInput) {
        Project project = this.getProjects().stream().filter(t -> t.equals(projectInput)).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il ruolo non ha il progetto con id: [" + projectInput.getId() + "]"));
        getInnerCategory(project.getCategory());
        return project;
    }

    protected DesignerRole getInnerDesignerInTeam(User user, Project projectInput) {
        return getInnerProject(projectInput).getTeam().getDesigners().stream().filter(d -> d.getIdUser().equals(user.getId())).findAny().orElseThrow(() ->
                new IllegalArgumentException("Il progetto: [" + projectInput.getId() + "] non possiede l'utente: [" + user.getId() + "]"));
    }
}