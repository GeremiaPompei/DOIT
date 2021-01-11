package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;

import java.util.Set;
import java.util.function.Function;

public class Controller {
    private User user;
    private UtilityFactory factory;
    private IResourceHandler resourceHandler;

    public Controller() {
        resourceHandler = new ResourceHandler();
        factory = new UtilityFactory(resourceHandler);
        factory.createCategory("Sport", "Descrizione.");
        factory.createCategory("Informatica", "Descrizione.");
        factory.createCategory("Domotica", "Descrizione.");
        factory.createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        factory.createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        factory.createProjectState(2, "TERMINALE", "Stato terminale.");
    }

    public void createUser(int id, String name, String surname, int birthdDay, String gender) throws Exception {
        if (searchUser(id) != null)
            throw new Exception("Esiste gia un user con stesso id!");
        factory.createUser(id, name, surname, birthdDay, gender);
    }

    public void addRole(String roleName, String idCategory) throws Exception {
        Category category = searchCategory(idCategory);
        if (category == null)
            throw new Exception("Categoria inesistente!");
        user.addRole(getRole(roleName), category);
    }

    public void removeRole(String roleName) throws Exception {
        user.removeRole(getRole(roleName));
    }

    public void addCategory(String roleName, String categoryName) throws Exception {
        user.getRole(getRole(roleName)).getCategories().add(searchCategory(categoryName));
    }

    public void removeCategory(String roleName, String categoryName) throws Exception {
        user.getRole(getRole(roleName)).getCategories().remove(searchCategory(categoryName));
    }

    public void login(int id) throws Exception {
        User user = searchUser(id);
        if (user == null)
            throw new Exception("L'utente non esiste!");
        this.user = user;
    }

    public void createProject(int id, String name, String description, String idCategory) throws Exception {
        if (searchProject(id) != null)
            throw new Exception("Esiste gia un progetto con stesso id!");
        Category cat = searchCategory(idCategory);
        if (cat == null)
            throw new Exception("Categoria inesistente!");
        this.user.getRole(ProjectProposerRole.class).createProject(id, name, description, cat, factory);
    }

    public void choosePgm(int idPgM, int idProject) throws Exception {
        Project p = searchProject(idProject);
        if (p.getTeam() != null)
            throw new Exception("Team gia inizializzato!");
        User usr = searchUser(idPgM);
        if (usr == null)
            throw new Exception("Non esiste l'utente con id: [" + idPgM + "]");
        this.user.getRole(ProjectProposerRole.class).createTeam(usr, p);
    }

    public Set<User> listPgm(String idCategory) throws Exception {
        return this.resourceHandler.search(User.class, this.user.getRole(ProjectProposerRole.class)
                .findProgramManagerList(searchCategory(idCategory)));
    }

    public void sendPr(int idDesigner) throws Exception {
        this.user.getRole(DesignerRole.class).createPartecipationRequest(searchProject(idDesigner).getTeam());
    }

    public Set<Project> listProjects(String idCategory) throws Exception {
        return this.resourceHandler.search(Project.class,
                this.user.getRole(DesignerRole.class).getProjects(searchCategory(idCategory)));
    }

    public void removePr(int idD, int idP, String reason) throws Exception {
        PartecipationRequest pr = this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(
                searchProject(idP).getTeam()).stream()
                .filter(p -> p.getDesigner().getId() == idD).findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(ProgramManagerRole.class).removePartecipationRequest(pr, reason);
    }

    public void addDesigner(int idD, int idP) throws Exception {
        PartecipationRequest pr = this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(
                searchProject(idP).getTeam()).stream().filter(p -> p.getDesigner().getId() == idD)
                .findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(ProgramManagerRole.class).addDesigner(pr);
    }

    public void choosePjm(int idD, int idP) throws Exception {
        this.user.getRole(ProgramManagerRole.class).setProjectManager(searchUser(idD), searchProject(idP));
    }

    public Set<Team> listTeams() throws Exception {
        return this.user.getRole(ProgramManagerRole.class).getTeams();
    }

    public Set<PartecipationRequest> listPR(int idProject) throws Exception {
        Project p = searchProject(idProject);
        if (p == null)
            throw new Exception("Progetto inesistente!");
        return this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(p.getTeam());
    }

    public Set<User> listDesigner(int idDesigner) throws Exception {
        return this.user.getRole(ProgramManagerRole.class).getDesigners(searchProject(idDesigner).getTeam());
    }

    public void upgradeState(int idProject) throws Exception {
        Project project = searchProject(idProject);
        Function<Set<ProjectState>, ProjectState> func = user.getRole(ProjectManagerRole.class).upgradeState(project);
        ProjectState ps = func.apply(resourceHandler.getRisorse().get(ProjectState.class));
        project.setProjectState(ps);
    }

    public Set<String> getRoles() {
        return resourceHandler.getRoles();
    }

    private User searchUser(int id) {
        return resourceHandler.searchOne(User.class, u -> u.getId() == id);
    }

    private Project searchProject(int id) {
        return resourceHandler.searchOne(Project.class, p -> p.getId() == id);
    }

    private Category searchCategory(String id) {
        return resourceHandler.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(id));
    }

    private Class<? extends Role> getRole(String roleName) throws Exception {
        return (Class<? extends Role>) Class.forName(
                "it.unicam.cs.ids.DOIT.model.roles.initial." + roleName);
    }

    public <T> Set<T> getRisorse(Class<T> t) {
        return resourceHandler.getRisorse().get(t);
    }

    public User getUser() {
        return user;
    }
}
