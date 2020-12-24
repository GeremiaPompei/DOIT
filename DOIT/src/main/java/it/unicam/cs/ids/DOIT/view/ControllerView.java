package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.*;
import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.roles.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.ProjectProposerRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerView {

    private User user;
    private Map<String, Map<String, Function<String[], String>>> commands;
    private GestoreRisorse gestoreRisorse;
    private ControllerChooseTeamMembers cChooseTeamMembers;
    private ControllerAddPR cAddPR;
    private ControllerInsertProposal cInsertProposal;
    private ControllerChooseProjectManager cChooseProjectManager;
    private ControllerChooseProgramManager cChooseProgramManager;

    public ControllerView() {
        gestoreRisorse = new GestoreRisorse();
        commands = new HashMap<>();
        loadCommands();
        this.cAddPR = new ControllerAddPR();
        this.cChooseProgramManager = new ControllerChooseProgramManager();
        this.cChooseProjectManager = new ControllerChooseProjectManager();
        this.cInsertProposal = new ControllerInsertProposal();
        this.cChooseTeamMembers = new ControllerChooseTeamMembers();
        setControllersUser();
    }

    private void setControllersUser() {
        this.cAddPR.setUser(user);
        this.cChooseProgramManager.setUser(user);
        this.cChooseProjectManager.setUser(user);
        this.cInsertProposal.setUser(user);
        this.cChooseTeamMembers.setUser(user);
    }

    public Map<String, Map<String, Function<String[], String>>> getCommands() {
        return commands;
    }

    private Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", this::createUser);
        map.put("add-role", this::addRole);
        map.put("login", this::login);
        map.put("help", (s) -> " > create idUser nameUser surnameUser [generality1, generality2...]"
                + "\n > add-role nameRole categoryName \n > login idUser");
        return map;
    }

    private String createUser(String[] s) {
        return manageFunc(() ->
        {
            int id = Integer.parseInt(s[1]);
            if (searchUser(id) != null)
                throw new Exception("Esiste gia un user con stesso id!");
            gestoreRisorse.getRisorse().get(User.class)
                    .add(new User(id, s[2], s[3], Integer.parseInt(s[4]), s[5]));
        });
    }

    private String addRole(String[] s) {
        return manageFunc(() -> {
            Category cat = searchCategory(s[2]);
            if (cat == null)
                throw new Exception("Categoria inesistente!");
            user.addRole((Class<? extends Role>) Class.forName("it.unicam.cs.ids.DOIT.model.roles." +
                    gestoreRisorse.searchOne(String.class, r -> r.equalsIgnoreCase(s[1]))), cat);
            loadCommands();
        });
    }

    private String login(String[] s) {
        return manageFunc(() -> {
            User user = searchUser(Integer.parseInt(s[1]));
            if (user == null)
                throw new Exception("L'utente non esiste!");
            this.user = user;
            setControllersUser();
            loadCommands();
        });
    }

    private Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> gestoreRisorse.getRisorse().get(User.class) + "");
        map.put("projects", (s) -> gestoreRisorse.getRisorse().get(Project.class) + "");
        map.put("categories", (s) -> gestoreRisorse.getRisorse().get(Category.class) + "");
        map.put("roles", (s) -> gestoreRisorse.getRisorse().get(String.class) + "");
        return map;
    }

    private Map<String, Function<String[], String>> projectProposerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", this::createProject);
        map.put("choose-pgm", this::choosePgm);
        map.put("list-pgm", this::listPgm);
        map.put("help", (s) -> " > create idProject nameProject descriptionProject categoryProject\n > choose-pgm " +
                "idUser idProject\n > list-pgm nameCategory");
        return map;
    }

    private String createProject(String[] s) {
        return manageFunc(() -> {
            int id = Integer.parseInt(s[1]);
            if (searchProject(id) != null)
                throw new Exception("Esiste gia un progetto con stesso id!");
            Category cat = searchCategory(s[4]);
            if (cat == null)
                throw new Exception("Categoria inesistente!");
            gestoreRisorse.getRisorse().get(Project.class).add(cInsertProposal.createProject(id, s[2], s[3], cat));
        });
    }

    private String choosePgm(String[] s) {
        return manageFunc(() -> {
            Project p = searchProject(Integer.parseInt(s[2]));
            if (p.getTeam() != null)
                throw new Exception("Team gia inizializzato!");
            int id = Integer.parseInt(s[1]);
            User usr = searchUser(id);
            if (usr == null)
                throw new Exception("Non esiste l'utente con id: [" + id + "]");
            cChooseProgramManager.initTeam(usr, p);
        });
    }

    private String listPgm(String[] s) {
        return manageException(() ->
                gestoreRisorse.search(User.class, cChooseProgramManager.findProgramManagerList(searchCategory(s[1])))
                        .toString());
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("send-pr", this::sendPr);
        map.put("list-projects", this::listProjects);
        map.put("help", (s) -> " > send-pr idProject \n > list-projects nameCategory");
        return map;
    }

    private String sendPr(String[] s) {
        return manageFunc(() -> {
            int id = Integer.parseInt(s[1]);
            cAddPR.addPartecipationRequest(gestoreRisorse.searchOne(Project.class, cAddPR.getProject(id)));
        });
    }

    private String listProjects(String[] s) {
        return manageException(() ->
                gestoreRisorse.search(Project.class, cAddPR.getProjects(searchCategory(s[1]))).toString());
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("remove-pr", this::removePr);
        map.put("add-designer", this::addDesigner);
        map.put("choose-pjm", this::choosePjm);
        map.put("list-teams", this::listTeams);
        map.put("list-pr", this::listPR);
        map.put("list-d", this::listDesigner);
        map.put("help", (s) -> " > add-designer idDesigner \n > remove-pr idDesigner reason \n > choose-pjm idDesigner "
                + "idProject \n > list-d idProject \n > list-teams \n > list-pr idProject");
        return map;
    }

    private String removePr(String[] s) {
        return manageFunc(() -> {
            PartecipationRequest pr = searchPR(Integer.parseInt(s[1]));
            if (pr == null)
                throw new Exception("Partecipation request non esistente!");
            cChooseTeamMembers.removePartecipationRequest(pr, List.of(s).subList(2, s.length).stream()
                    .reduce((x, y) -> x + y).get());
        });
    }

    private String addDesigner(String[] s) {
        return manageFunc(() -> {
            PartecipationRequest pr = searchPR(Integer.parseInt(s[1]));
            if (pr == null)
                throw new Exception("Partecipation request non esistente!");
            cChooseTeamMembers.addDesigner(pr);
        });
    }

    private String choosePjm(String[] s) {
        return manageFunc(() -> {
            int idU = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            cChooseProjectManager.becomeProjectManager(searchUser(idU), searchProject(idP));
        });
    }

    private String listTeams(String[] s) {
        return manageException(() -> cChooseTeamMembers.getTeams().toString());
    }

    private String listPR(String[] s) {
        return manageException(() -> {
            Project p = searchProject(Integer.parseInt(s[1]));
            if (p == null)
                return "Progetto inesistente!";
            return cChooseTeamMembers.getPartecipationRequests(p.getTeam()).toString();
        });
    }

    private String listDesigner(String[] s) {
        return manageException(() ->
                cChooseProjectManager.getDesigners(searchProject(Integer.parseInt(s[1]))).toString());
    }

    private User searchUser(int id) {
        return gestoreRisorse.searchOne(User.class, u -> u.getId() == id);
    }

    private Project searchProject(int id) {
        return gestoreRisorse.searchOne(Project.class, p -> p.getId() == id);
    }

    private Category searchCategory(String id) {
        return gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(id));
    }

    private PartecipationRequest searchPR(int id) {
        return ((Set<Project>) gestoreRisorse.getRisorse().get(Project.class)).stream()
                .map(p -> p.getTeam().getPartecipationRequests()).reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                }).get().stream().filter(x -> x.getDesigner().getId() == id).findAny().get();
    }

    private void loadCommands() {
        commands.clear();
        commands.put("user", userMap());
        commands.put("list", listMap());
        if (user != null) {
            Set<Class<? extends Role>> set = user.getRoles().stream().map(r -> r.getClass()).collect(Collectors.toSet());
            if (set.contains(ProjectProposerRole.class))
                commands.put("project-proposer", projectProposerMap());
            if (set.contains(DesignerRole.class))
                commands.put("designer", designerMap());
            if (set.contains(ProgramManagerRole.class))
                commands.put("program-manager", programManagerMap());
        }
    }

    private String manageException(ToString ts) {
        try {
            return ts.accept();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String manageFunc(ConsumerException ce) {
        return manageException(() -> {
            ce.accept();
            return "Success!";
        });
    }

    @FunctionalInterface
    public interface ConsumerException {
        void accept() throws Exception;
    }

    @FunctionalInterface
    public interface ToString {
        String accept() throws Exception;
    }

}
