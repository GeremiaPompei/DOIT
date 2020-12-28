package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.Controller;
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

    private Controller controller;
    private Map<String, Map<String, Function<String[], String>>> commands;

    public ControllerView() {
        controller = new Controller();
        commands = new HashMap<>();
        loadCommands();
    }

    public Map<String, Map<String, Function<String[], String>>> getCommands() {
        return commands;
    }

    private Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", this::createUser);
        map.put("add-role", this::addRole);
        map.put("login", this::login);
        map.put("help", (s) -> " > create idUser nameUser surnameUser birthYearUeser genderUser"
                + "\n > add-role nameRole categoryName \n > login idUser");
        return map;
    }

    private String createUser(String[] s) {
        return manageRunnable(() ->
                this.controller.createUser(Integer.parseInt(s[1]), s[2], s[3], Integer.parseInt(s[4]), s[5]));
    }

    private String addRole(String[] s) {
        return manageRunnable(() -> {
            Class<? extends Role> role = (Class<? extends Role>) Class.forName("it.unicam.cs.ids.DOIT.model.roles." +
                    GestoreRisorse.getInstance().searchOne(String.class, r -> r.equalsIgnoreCase(s[1])));
            this.controller.addRole(role, s[2]);
            loadCommands();
        });
    }

    private String login(String[] s) {
        return manageRunnable(() -> {
            this.controller.login(Integer.parseInt(s[1]));
            loadCommands();
        });
    }

    private Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> GestoreRisorse.getInstance().getRisorse().get(User.class) + "");
        map.put("projects", (s) -> GestoreRisorse.getInstance().getRisorse().get(Project.class) + "");
        map.put("categories", (s) -> GestoreRisorse.getInstance().getRisorse().get(Category.class) + "");
        map.put("roles", (s) -> GestoreRisorse.getInstance().getRisorse().get(String.class) + "");
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
        return manageRunnable(() -> this.controller.createProject(Integer.parseInt(s[1]), s[2], s[3], s[4]));
    }

    private String choosePgm(String[] s) {
        return manageRunnable(() -> this.controller.choosePgm(Integer.parseInt(s[1]), Integer.parseInt(s[2])));
    }

    private String listPgm(String[] s) {
        if (s.length == 1)
            return "Aggiungere una categoria!";
        return manageException(() -> this.controller.listPgm(s[1]).toString());
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("send-pr", this::sendPr);
        map.put("list-projects", this::listProjects);
        map.put("help", (s) -> " > send-pr idProject \n > list-projects nameCategory");
        return map;
    }

    private String sendPr(String[] s) {
        return manageRunnable(() -> this.controller.sendPr(Integer.parseInt(s[1])));
    }

    private String listProjects(String[] s) {
        if (s.length == 1)
            return "Aggiungere una categoria!";
        return manageException(() -> this.controller.listProjects(s[1]).toString());
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("remove-pr", this::removePr);
        map.put("add-designer", this::addDesigner);
        map.put("choose-pjm", this::choosePjm);
        map.put("list-teams", this::listTeams);
        map.put("list-pr", this::listPR);
        map.put("list-d", this::listDesigner);
        map.put("help", (s) -> " > add-designer idDesigner idTeam \n > remove-pr idDesigner idTeam reason \n > choose-pjm idDesigner "
                + "idProject \n > list-d idProject \n > list-teams \n > list-pr idProject");
        return map;
    }

    private String removePr(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            String reason = List.of(s).subList(2, s.length).stream().reduce((x, y) -> x + y).get();
            this.controller.removePr(idD, idP, reason);
        });
    }

    private String addDesigner(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            this.controller.addDesigner(idD, idP);
        });
    }

    private String choosePjm(String[] s) {
        return manageRunnable(() -> {
            int idU = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            this.controller.choosePjm(idU, idP);
        });
    }

    private String listTeams(String[] s) {
        return manageException(() -> this.controller.listTeams().toString());
    }

    private String listPR(String[] s) {
        if (s.length == 1)
            return "Aggiungere l'id di un progetto!";
        return manageException(() -> this.controller.listPR(Integer.parseInt(s[1])).toString());
    }

    private String listDesigner(String[] s) {
        if (s.length == 1)
            return "Aggiungere l'id di un progetto!";
        return manageException(() -> this.controller.listDesigner(Integer.parseInt(s[1])).toString());
    }

    private void loadCommands() {
        commands.clear();
        commands.put("user", userMap());
        commands.put("list", listMap());
        if (controller.getUser() != null) {
            Set<Class<? extends Role>> set = controller.getUser().getRoles().stream().map(r -> r.getClass())
                    .collect(Collectors.toSet());
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

    private String manageRunnable(RunnableException re) {
        return manageException(() -> {
            re.accept();
            return "Success!";
        });
    }

    @FunctionalInterface
    public interface RunnableException {
        void accept() throws Exception;
    }

    @FunctionalInterface
    public interface ToString {
        String accept() throws Exception;
    }

}
