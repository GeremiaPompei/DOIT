package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role implements ISubscriber {

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Notification> notications;

    public Role() {
    }

    public Role(User user, Category category) {
        this.idUser = user.getId();
        projects = new HashSet<>();
        categories = new HashSet<>();
        history = new HashSet<>();
        this.categories.add(category);
        this.notications = new ArrayList<>();
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
            throw new IllegalArgumentException("Can't remove a category if you only have one!");
        if (this.projects.stream().filter(p -> p.getCategory().equals(category)).findAny().orElse(null) != null)
            throw new IllegalArgumentException("Can't remove a category if you already have a project with that category!");
        this.categories.remove(category);
    }

    public void notify(String notification) {
        this.notications.add(new Notification(notification));
    }

    public void removeNotifications() {
        this.notications.removeAll(this.notications);
    }

    public List<Notification> getNotications() {
        return notications;
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
                .orElseThrow(() -> new IllegalArgumentException("This user doesn't own the category!"));
    }

    protected PartecipationRequest<DesignerRole> getInnerDesignerRequest(PartecipationRequest<DesignerRole> prInput) {
        PartecipationRequest<DesignerRole> pr = this.getProjects().stream()
                .filter(p -> p.equals(prInput.getProject()))
                .map(p -> p.getTeam())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("You don't own the project!"))
                .getDesignerRequest().stream()
                .filter(t -> t.getPendingRole().equals(prInput.getPendingRole()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no request by this role!"));
        getInnerCategory(prInput.getProject().getCategory());
        return pr;
    }

    protected PartecipationRequest<ProgramManagerRole> getInnerProgramManagerRequest(PartecipationRequest<ProgramManagerRole> prInput) {
        PartecipationRequest<ProgramManagerRole> pr = this.getProjects().stream()
                .filter(p -> p.equals(prInput.getProject()))
                .map(p -> p.getTeam())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("You don't own the project!"))
                .getProgramManagerRequest().stream()
                .filter(t -> t.getPendingRole().equals(prInput.getPendingRole()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no request by this role!"));
        getInnerCategory(prInput.getProject().getCategory());
        return pr;
    }

    protected Project getInnerProject(Project projectInput) {
        Project project = this.getProjects().stream().filter(t -> t.equals(projectInput)).findAny().orElseThrow(() ->
                new IllegalArgumentException("This role doesn't have the project whose id is: [" + projectInput.getId() + "]"));
        getInnerCategory(project.getCategory());
        return project;
    }

    protected DesignerRole getInnerDesignerInTeam(User user, Project projectInput) {
        return getInnerProject(projectInput).getTeam().getDesigners().stream().filter(d -> d.getIdUser().equals(user.getId())).findAny().orElseThrow(() ->
                new IllegalArgumentException("The project: [" + projectInput.getId() + "] doesn't own the user: [" + user.getId() + "]"));
    }
}