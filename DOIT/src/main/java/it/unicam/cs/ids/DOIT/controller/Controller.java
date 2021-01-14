package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.history.IHistory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.project.IProjectState;
import it.unicam.cs.ids.DOIT.project.ITeam;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.role.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.service.IResourceHandler;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Controller implements IController {
    private IUser user;
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;
    private Set<Class<? extends IRole>> choosableRoles;

    public Controller() {
        this.resourceHandler = ServicesHandler.getResourceHandler();
        this.factory = ServicesHandler.getFactoryModel();
        choosableRoles = new HashSet<>();
        choosableRoles.add(ProgramManagerRole.class);
        choosableRoles.add(ProjectProposerRole.class);
        choosableRoles.add(DesignerRole.class);
        choosableRoles.add(ProjectManagerRole.class);
        factory.createCategory("Sport", "Descrizione.");
        factory.createCategory("Informatica", "Descrizione.");
        factory.createCategory("Domotica", "Descrizione.");
        factory.createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        factory.createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        factory.createProjectState(2, "TERMINALE", "Stato terminale.");

        //TODO prova
        try {
            user = factory.createUser(1, "1", "1", 1, "1");
            user.addRole(ProjectProposerRole.class, searchCategory("sport"), factory);
            user.addRole(ProgramManagerRole.class, searchCategory("sport"), factory);
            user.addRole(DesignerRole.class, searchCategory("sport"), factory);
            user.getRole(ProjectProposerRole.class).createProject(9, "9", "9", searchCategory("sport"), factory);
            user.getRole(ProjectProposerRole.class).createTeam(searchUser(1), searchProject(9), factory);
            user.getRole(ProgramManagerRole.class).addDesigner(user.getRole(DesignerRole.class)
                    .createPartecipationRequest(searchProject(9).getTeam(), factory));
            user.getRole(ProgramManagerRole.class).setProjectManager(searchUser(1), searchProject(9), ProjectManagerRole.class);
            //user.getRole(IProjectManagerRole.class).insertEvaluation(searchUser(1), 3,searchProject(9));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (roleName.equalsIgnoreCase("ProjectManagerRole"))
            throw new Exception();
        user.addRole(getRole(roleName), category, factory);
    }

    public void removeRole(String roleName) {
        user.removeRole(getRole(roleName));
    }

    public void addCategory(String roleName, String categoryName) throws Exception {
        user.getRole(getRole(roleName)).addCategory(searchCategory(categoryName));
    }

    public void removeCategory(String roleName, String categoryName) throws Exception {
        user.getRole(getRole(roleName)).removeCategory(searchCategory(categoryName));
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
                .filter(p -> p.getUser().getId() == idD).findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(IProgramManagerRole.class).removePartecipationRequest(pr, reason);
    }

    public void addDesigner(int idD, int idP) throws Exception {
        IPartecipationRequest pr = this.user.getRole(ProgramManagerRole.class).getPartecipationRequests(
                searchProject(idP).getTeam()).stream().filter(p -> p.getUser().getId() == idD)
                .findAny().orElse(null);
        if (pr == null)
            throw new Exception("Partecipation request non esistente!");
        this.user.getRole(IProgramManagerRole.class).addDesigner(pr);
    }

    public void choosePjm(int idD, int idP) throws Exception {
        this.user.getRole(IProgramManagerRole.class).setProjectManager(searchUser(idD), searchProject(idP), ProjectManagerRole.class);
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

    public Set<IUser> listDesignerForProgramManager(int idProject) throws Exception {
        return this.user.getRole(IProgramManagerRole.class).getDesigners(searchProject(idProject).getTeam());
    }

    @Override
    public Set<IUser> listDesignerForPrjManager(int idDesigner) throws Exception {
        return this.user.getRole(IProjectManagerRole.class).getDesigners(searchProject(idDesigner).getTeam());
    }

    public void insertEvaluation(int idDesigner, int idProject, int evaluation) throws Exception {
        IProject project = searchProject(idProject);
        IUser user = searchUser(idDesigner);
        Function<Set<IProjectState>, IProjectState> nextFunc = user.getRole(IProjectManagerRole.class).upgradeState(project);
        IProjectState ps = nextFunc.apply(resourceHandler.search(IProjectState.class, s -> true));
        if (!(project.getProjectState() != null && ps == null))
            throw new Exception("Il progetto deve possedere lo stato terminale!");
        this.user.getRole(IProjectManagerRole.class).insertEvaluation(user, evaluation, project);

    }

    public void exitAll(int idProject) throws Exception {
        IProject project = searchProject(idProject);
        Function<Set<IProjectState>, IProjectState> nextFunc = user.getRole(IProjectManagerRole.class).upgradeState(project);
        IProjectState ps = nextFunc.apply(resourceHandler.search(IProjectState.class, s -> true));
        if (!(project.getProjectState() != null && ps == null))
            throw new Exception("Il progetto deve possedere lo stato terminale!");
        this.user.getRole(IProjectManagerRole.class).exitAll(project);
    }

    @Override
    public void upgradeState(int idProject) throws Exception {
        IProject project = searchProject(idProject);
        Function<Set<IProjectState>, IProjectState> nextFunc = user.getRole(IProjectManagerRole.class).upgradeState(project);
        IProjectState ps = nextFunc.apply(resourceHandler.search(IProjectState.class, s -> true));
        if (project.getProjectState() != null && ps == null)
            throw new IllegalArgumentException("Lo stato dwl progetto è terminale!");
        project.setProjectState(ps);
    }

    @Override
    public void downgradeState(int idProject) throws Exception {
        IProject project = searchProject(idProject);
        Function<Set<IProjectState>, IProjectState> prevFunc = user.getRole(IProjectManagerRole.class).downgradeState(project);
        IProjectState ps = prevFunc.apply(resourceHandler.search(IProjectState.class, s -> true));
        if (project.getProjectState() != null && ps == null)
            throw new IllegalArgumentException("Lo stato dwl progetto è iniziale!");
        project.setProjectState(ps);
    }

    @Override
    public String visualizeState(int idProject) {
        IProject project = searchProject(idProject);
        return project.getProjectState().toString();
    }

    public Set<IProject> listProjectsOwnedByPrjManager() throws Exception {
        return user.getRole(IProjectManagerRole.class).getProjects();
    }

    public Set<Class<? extends IRole>> getChoosableRoles() {
        return choosableRoles;
    }

    @Override
    public Integer getEvaluation(int idProject) throws RoleException {
        IProject project = searchProject(idProject);
        return user.getRole(IDesignerRole.class).getEvaluations().get(project);
    }

    public void removeDesigner(int idD, int idT) throws Exception {
        ITeam team = this.user.getRole(ProgramManagerRole.class).getTeams().stream()
                .filter(p -> p.getProject().getId() == idT).findAny().orElse(null);
        IUser designer = this.user.getRole(ProgramManagerRole.class).getDesigners(team).stream()
                .filter(p -> p.getId() == idD).findAny().orElse(null);
        if (team == null)
            throw new Exception("Team non esistente");
        if (designer == null)
            throw new Exception("Designer non esistente");
        this.user.getRole(IProgramManagerRole.class).removeDesigner(designer, team);
    }

    public void openRegistrations(int idT) throws Exception {
        ITeam team = this.user.getRole(ProgramManagerRole.class).getTeams()
                .stream().filter(p -> p.getProject().getId() == idT).findAny().orElse(null);
        if (team == null)
            throw new Exception("Team non esistente");
        this.user.getRole(IProgramManagerRole.class).openRegistrations(team);
    }

    public void closeRegistrations(int idT) throws Exception {
        ITeam team = this.user.getRole(ProgramManagerRole.class).getTeams()
                .stream().filter(p -> p.getProject().getId() == idT).findAny().orElse(null);
        if (team == null)
            throw new Exception("Team non esistente");
        this.user.getRole(IProgramManagerRole.class).closeRegistrations(team);
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

    private Class<? extends IRole> getRole(String roleName) {
        return choosableRoles.stream().filter(c -> c.getSimpleName().equalsIgnoreCase(roleName)).findAny().orElse(null);
    }

    @Override
    public IHistory visualizeHistory(String role) throws RoleException {
        return user.getRole(getRole(role)).getCronology();
    }

    public <T> Set<T> getRisorse(Class<T> t) {
        return resourceHandler.search(t, s -> true);
    }

    public IUser getUser() {
        return user;
    }
}
