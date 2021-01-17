package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.user.IUserHandler;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.role.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerView {

    private IUserHandler controller;
    private Map<String, Map<String, Function<String[], String>>> commands;

    public ControllerView(IUserHandler controller) {
        this.controller = controller;
        commands = new HashMap<>();
        loadCommands();
        ServicesHandler.getInstance().getFactoryModel().createCategory("SPORT", "description...");
        ServicesHandler.getInstance().getFactoryModel().createCategory("INFORMATICA", "description...");
        ServicesHandler.getInstance().getFactoryModel().createCategory("DOMOTICA", "description...");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INITIALIZATION", "description...");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "IN PROGRESS", "description...");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINAL", "description...");

        createUser(new String[]{"", "1", "1", "1", "1"});
        int idUser = ServicesHandler.getInstance().getResourceHandler().getAllUsers().stream().findAny().orElse(null).getId();
        login(new String[]{"", idUser + ""});
        addRole(new String[]{"", "ProjectProposerRole", "sport"});
        addRole(new String[]{"", "ProgramManagerRole", "sport"});
        addRole(new String[]{"", "DesignerRole", "sport"});
        createProject(new String[]{"", "9", "9", "sport"});
        int idProject = ServicesHandler.getInstance().getResourceHandler().getAllProjects().stream().findAny().orElse(null).getId();
        choosePgm(new String[]{"", idUser + "", idProject + ""});
        openRegistrations(new String[]{"", idProject + ""});
        sendPr(new String[]{"", idProject + ""});
        addDesigner(new String[]{"", idUser + "", idProject + ""});
        choosePjm(new String[]{"", idUser + "", idProject + ""});
        upgradeState(new String[]{"", idProject + ""});
        upgradeState(new String[]{"", idProject + ""});

        System.err.println("----> " + idUser + " " + idProject);

    }

    public Map<String, Map<String, Function<String[], String>>> getCommands() {
        return commands;
    }

    private Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("list", (s) -> ServicesHandler.getInstance().getResourceHandler().getAllUsers() + "");
        map.put("create", this::createUser);
        map.put("add-role", this::addRole);
        map.put("remove-role", this::removeRole);
        map.put("login", this::login);
        map.put("logout", this::logout);
        map.put("help", (s) -> " > create idUser nameUser surnameUser birthYearUeser genderUser"
                + "\n > add-role nameRole categoryName \n > login idUser");
        return map;
    }

    private String createUser(String[] s) {
        return manageRunnable(() ->
                this.controller.signIn(s[1], s[2], s[3], s[4]));
    }

    private String addRole(String[] s) {
        return manageRunnable(() -> {
            this.controller.addRole(s[1], s[2]);
            loadCommands();
        });
    }

    private String removeRole(String[] s) {
        return manageRunnable(() -> {
            this.controller.removeRole(s[1]);
            loadCommands();
        });
    }

    private String login(String[] s) {
        return manageRunnable(() -> {
            this.controller.login(Integer.parseInt(s[1]));
            loadCommands();
        });
    }

    private String logout(String[] s) {
        return manageRunnable(() -> {
            this.controller.logOut();
            loadCommands();
        });
    }

    private Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> ServicesHandler.getInstance().getResourceHandler().getAllUsers() + "");
        map.put("projects", (s) -> ServicesHandler.getInstance().getResourceHandler().getAllProjects() + "");
        map.put("categories", (s) -> ServicesHandler.getInstance().getResourceHandler().getAllCategories() + "");
        map.put("roles", (s) -> controller.getChoosableRoles().stream().map(c -> c.getSimpleName()).collect(Collectors.toSet()) + "");
        return map;
    }

    private Map<String, Function<String[], String>> projectProposerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", this::createProject);
        map.put("choose-pgm", this::choosePgm);
        map.put("list-pgm", this::listPgm);
        map.putAll(roleActions("ProjectProposerRole"));
        map.put("help", (s) -> " > create idProject nameProject descriptionProject categoryProject \n > choose-pgm " +
                "idUser idProject \n > list-pgm nameCategory \n > add-category nameCategory \n > remove-category nameCategory");
        return map;
    }

    private String createProject(String[] s) {
        return manageRunnable(() -> this.controller.getUser().getRole(ProjectProposerRole.class).createProject(s[1], s[2], s[3]));
    }

    private String choosePgm(String[] s) {
        return manageRunnable(() -> this.controller.getUser().getRole(ProjectProposerRole.class).createTeam(Integer.parseInt(s[1]), Integer.parseInt(s[2])));
    }

    private String listPgm(String[] s) {
        if (s.length == 1)
            return "Aggiungere una categoria!";
        return manageException(() -> this.controller.getUser().getRole(ProjectProposerRole.class).findProgramManagerList(s[1]).toString());
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("send-pr", this::sendPr);
        map.put("list-projects", this::listProjects);
        map.putAll(roleActions("DesignerRole"));
        map.put("evaluations", this::visualizeEvaluations);
        map.put("help", (s) -> " > send-pr idProject \n > list-projects nameCategory \n > add-category nameCategory " +
                "\n > remove-category nameCategory");
        return map;
    }

    private String visualizeEvaluations(String[] s) {
        return manageException(() -> controller.getUser().getRole(DesignerRole.class).getEvaluations().toString());
    }

    private String sendPr(String[] s) {
        return manageRunnable(() -> this.controller.getUser().getRole(DesignerRole.class).createPartecipationRequest(Integer.parseInt(s[1])));
    }

    private String listProjects(String[] s) {
        if (s.length == 1)
            return "Aggiungere una categoria!";
        return manageException(() -> this.controller.getUser().getRole(DesignerRole.class).getProjects(s[1]).toString());
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("remove-pr", this::removePr);
        map.put("add-designer", this::addDesigner);
        map.put("remove-designer", this::removeDesigner);
        map.put("choose-pjm", this::choosePjm);
        map.put("list-teams", this::listTeams);
        map.put("open-registrations", this::openRegistrations);
        map.put("close-registrations", this::closeRegistrations);
        map.put("list-pr", this::listPR);
        map.put("list-d", this::listDesignerForProgramManager);
        map.putAll(roleActions("ProgramManagerRole"));
        map.put("help", (s) -> " > add-designer idDesigner idTeam \n > remove-pr idDesigner idTeam reason \n > choose-pjm idDesigner "
                + "idProject \n > list-d idProject \n > list-teams \n > list-pr idProject \n > add-category nameCategory " +
                "\n > remove-category nameCategory");
        return map;
    }

    private String removeDesigner(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idT = Integer.parseInt(s[2]);
            this.controller.getUser().getRole(ProgramManagerRole.class).removeDesigner(idD, idT);
        });
    }

    private String openRegistrations(String[] s) {
        return manageRunnable(() -> {
            int idT = Integer.parseInt(s[1]);
            this.controller.getUser().getRole(ProgramManagerRole.class).openRegistrations(idT);
        });
    }

    private String closeRegistrations(String[] s) {
        return manageRunnable(() -> {
            int idT = Integer.parseInt(s[1]);
            this.controller.getUser().getRole(ProgramManagerRole.class).closeRegistrations(idT);
        });
    }

    private String removePr(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            String reason = List.of(s).subList(2, s.length).stream().reduce((x, y) -> x + y).get();
            this.controller.getUser().getRole(ProgramManagerRole.class).removePR(idD, idP, reason);
        });
    }

    private String addDesigner(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            this.controller.getUser().getRole(ProgramManagerRole.class).acceptPR(idD, idP);
        });
    }

    private String choosePjm(String[] s) {
        return manageRunnable(() -> {
            int idU = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            this.controller.getUser().getRole(ProgramManagerRole.class).setProjectManager(idU, idP);
            loadCommands();
        });
    }

    private Map<String, Function<String[], String>> roleActions(String role) {
        Map<String, Function<String[], String>> map = new HashMap<>();
        if (!role.equalsIgnoreCase("ProjectManagerRole")) {
            map.put("add-category", s -> addCategory(s, role));
            map.put("remove-category", s -> removeCategory(s, role));
        }
        map.put("visualize-history", s -> visualizeHistory(role));
        return map;
    }

    private String visualizeHistory(String role) {
        return manageException(() ->
                this.controller.getUser().getRole(controller.getRole(role)).getHystory().toString());
    }

    private String addCategory(String[] s, String role) {
        return manageRunnable(() -> {
            this.controller.getUser().getRole(controller.getRole(role)).addCategory(s[1]);
        });
    }

    private String removeCategory(String[] s, String role) {
        return manageRunnable(() -> {
            this.controller.getUser().getRole(controller.getRole(role)).removeCategory(s[1]);
        });
    }

    private String listTeams(String[] s) {
        return manageException(() -> this.controller.getUser().getRole(ProgramManagerRole.class).getTeams().toString());
    }

    private String listPR(String[] s) {
        if (s.length == 1)
            return "Aggiungere l'id di un progetto!";
        return manageException(() -> this.controller.getUser().getRole(ProgramManagerRole.class).getPartecipationRequests(Integer.parseInt(s[1])).toString());
    }

    private String listDesignerForProgramManager(String[] s) {
        if (s.length == 1)
            return "Aggiungere l'id di un progetto!";
        return manageException(() -> this.controller.getUser().getRole(ProgramManagerRole.class).getDesigners(Integer.parseInt(s[1])).toString());
    }

    private Map<String, Function<String[], String>> projectManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("list-projects", this::listProjectsOwned);
        map.put("upgrade-state", this::upgradeState);
        map.put("downgrade-state", this::downgradeState);
        map.put("list-d", this::listDesignerForPrjManager);
        map.put("evaluate-d", this::evaluateDesigner);
        map.put("exitAll", this::exitAll);
        map.putAll(roleActions("ProjectManagerRole"));
        map.put("visualize-state", this::visualizeState);
        map.put("help", (s) -> " > list-projects  \n > upgrade-state idProject \n > downgrade-state idProject \n " +
                "> list-d idProject \n > evaluate-d idDesigner idProject evaluation(0-5)\n > exitAll idProject\n " +
                "> visualize-state idProject");
        return map;
    }

    private String listProjectsOwned(String[] s) {
        return manageException(() -> this.controller.getUser().getRole(ProjectManagerRole.class).getTeams().toString());
    }

    private String visualizeState(String[] s) {
        return manageException(() -> controller.getUser().getRole(ProjectManagerRole.class).getProjectState(Integer.parseInt(s[1])).toString());
    }

    private String exitAll(String[] s) {
        return manageRunnable(() -> controller.getUser().getRole(ProjectManagerRole.class).exitAll(Integer.parseInt(s[1])));
    }

    private String evaluateDesigner(String[] s) {
        return manageRunnable(() -> {
            int idD = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            int evaluation = Integer.parseInt(s[3]);
            this.controller.getUser().getRole(ProjectManagerRole.class).insertEvaluation(idD, idP, evaluation);
        });
    }

    private String listDesignerForPrjManager(String[] strings) {
        return manageException(() -> controller.getUser().getRole(ProjectManagerRole.class).getDesigners(Integer.parseInt(strings[1])).toString());
    }

    private String downgradeState(String[] s) {
        return manageRunnable(() -> this.controller.getUser().getRole(ProjectManagerRole.class).downgradeState(Integer.parseInt(s[1])));
    }

    private String upgradeState(String[] s) {
        return manageRunnable(() -> this.controller.getUser().getRole(ProjectManagerRole.class).upgradeState(Integer.parseInt(s[1])));
    }

    private void loadCommands() {
        commands.clear();
        commands.put("user", userMap());
        commands.put("list", listMap());
        if (controller.getUser() != null) {
            Set<Class<? extends IRole>> set = controller.getUser().getRoles().stream().map(r -> r.getClass())
                    .collect(Collectors.toSet());
            if (set.contains(ProjectProposerRole.class))
                commands.put("project-proposer", projectProposerMap());
            if (set.contains(DesignerRole.class))
                commands.put("designer", designerMap());
            if (set.contains(ProgramManagerRole.class))
                commands.put("program-manager", programManagerMap());
            if (set.contains(ProjectManagerRole.class))
                commands.put("project-manager", projectManagerMap());
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
