package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.*;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.storage.FactoryStorage;
import it.unicam.cs.ids.DOIT.storage.IResourceHandler;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Controller implements IController {
    private IUser user;
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;
    private Set<String> roles;

    public Controller(IFactoryModel factory) {
        this.resourceHandler = FactoryStorage.getResouceHandler();
        this.factory = factory;
        roles = new HashSet<>();
        Arrays.stream(new File("src/main/java/it/unicam/cs/ids/DOIT/model/roles/initial").list())
                .forEach(s -> roles.add(s.replace(".java", "")));
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
        ICategory category = searchCategory(idCategory);
        if (category == null)
            throw new Exception("Categoria inesistente!");
        user.addRole(getRole(roleName), category, factory);
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
        IUser user = searchUser(id);
        if (user == null)
            throw new Exception("L'utente non esiste!");
        this.user = user;
    }

    public void createProject(int id, String name, String description, String idCategory) throws Exception {
        if (searchProject(id) != null)
            throw new Exception("Esiste gia un progetto con stesso id!");
        ICategory cat = searchCategory(idCategory);
        if (cat == null)
            throw new Exception("Categoria inesistente!");
        this.user.getRole(IProjectProposerRole.class).createProject(id, name, description, cat, factory);
    }

    public void choosePgm(int idPgM, int idProject) throws Exception {
        IProject p = searchProject(idProject);
        if (p.getTeam() != null)
            throw new Exception("Team gia inizializzato!");
        IUser usr = searchUser(idPgM);
        if (usr == null)
            throw new Exception("Non esiste l'utente con id: [" + idPgM + "]");
        this.user.getRole(IProjectProposerRole.class).createTeam(usr, p, factory);
    }

    public Set<IUser> listPgm(String idCategory) throws Exception {
        return this.resourceHandler.search(IUser.class, this.user.getRole(IProjectProposerRole.class)
                .findProgramManagerList(searchCategory(idCategory)));
    }

    public void sendPr(int idDesigner) throws Exception {
        this.user.getRole(IDesignerRole.class).createPartecipationRequest(searchProject(idDesigner).getTeam(), factory);
    }

    public Set<IProject> listProjects(String idCategory) throws Exception {
        return this.resourceHandler.search(IProject.class,
                this.user.getRole(IDesignerRole.class).getProjects(searchCategory(idCategory)));
    }

    public void removePr(int idD, int idP, String reason) throws Exception {
        IPartecipationRequest pr = this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(
                searchProject(idP).getTeam()).stream()
                .filter(p -> p.getDesigner().getId() == idD).findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, reason);
    }

    public void addDesigner(int idD, int idP) throws Exception {
        IPartecipationRequest pr = this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(
                searchProject(idP).getTeam()).stream().filter(p -> p.getDesigner().getId() == idD)
                .findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(IProgramManagerRole.class).addDesigner(pr);
    }

    public void choosePjm(int idD, int idP) throws Exception {
        this.user.getRole(IProgramManagerRole.class).setProjectManager(searchUser(idD), searchProject(idP));
    }

    public Set<ITeam> listTeams() throws Exception {
        return this.user.getRole(IProgramManagerRole.class).getTeams();
    }

    public Set<IPartecipationRequest> listPR(int idProject) throws Exception {
        IProject p = searchProject(idProject);
        if (p == null)
            throw new Exception("Progetto inesistente!");
        return this.user.getRole(IProgramManagerRole.class).getPartecipationRequests(p.getTeam());
    }

    public Set<IUser> listDesigner(int idDesigner) throws Exception {
        return this.user.getRole(IProgramManagerRole.class).getDesigners(searchProject(idDesigner).getTeam());
    }

    public void upgradeState(int idProject) throws Exception {
        IProject project = searchProject(idProject);
        Function<Set<IProjectState>, IProjectState> func = user.getRole(IProjectManagerRole.class).upgradeState(project);
        IProjectState ps = func.apply(resourceHandler.search(IProjectState.class, s -> true));
        project.setProjectState(ps);
    }

    public Set<String> getRoles() {
        return roles;
    }

    private IUser searchUser(int id) {
        return resourceHandler.searchOne(IUser.class, u -> u.getId() == id);
    }

    private IProject searchProject(int id) {
        return resourceHandler.searchOne(IProject.class, p -> p.getId() == id);
    }

    private ICategory searchCategory(String id) {
        return resourceHandler.searchOne(ICategory.class, c -> c.getName().equalsIgnoreCase(id));
    }

    private Class<? extends IRole> getRole(String roleName) throws Exception {
        return (Class<? extends IRole>) Class.forName(
                "it.unicam.cs.ids.DOIT.model.roles.initial." + roleName);
    }

    public <T> Set<T> getRisorse(Class<T> t) {
        return resourceHandler.search(t, s -> true);
    }

    public IUser getUser() {
        return user;
    }
}
