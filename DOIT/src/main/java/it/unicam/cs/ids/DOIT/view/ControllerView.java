package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.ControllerAddPR;
import it.unicam.cs.ids.DOIT.controller.ControllerChooseProgramManager;
import it.unicam.cs.ids.DOIT.controller.ControllerChooseTeamMembers;
import it.unicam.cs.ids.DOIT.controller.ControllerInsertProposal;
import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.model.Roles.DesignerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.Roles.ProjectProposerRole;

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

    public ControllerView() {
        gestoreRisorse = new GestoreRisorse();
        commands = new HashMap<>();
        loadCommands();
    }

    public Map<String, Map<String, Function<String[], String>>> getCommands() {
        return commands;
    }

    private Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", (s) -> consumerException(() ->
        {
            if (gestoreRisorse.searchOne(User.class, u -> u.getId() == Integer.parseInt(s[1])) != null)
                throw new Exception("Esiste gia un user con stesso id!");
            gestoreRisorse.getRisorse().get(User.class)
                    .add(new User(Integer.parseInt(s[1]), s[2], s[3], List.of(s).subList(4, s.length)));
        }));
        map.put("add-role", (s) -> consumerException(() -> {
            user.addRole((Class<? extends Role>) Class.forName(
                    gestoreRisorse.searchOne(Class.class, r -> r.getSimpleName().equalsIgnoreCase(s[1])).getName()),
                    gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[2])));
            loadCommands();
        }));
        map.put("login", (s) -> consumerException(() -> {
            user = gestoreRisorse.searchOne(User.class, u -> u.getId() == Integer.parseInt(s[1]));
            if (user == null)
                throw new Exception("L'utente non esiste!");
            loadCommands();
        }));
        map.put("help", (s) -> " > create idUser nameUser surnameUser [generality1, generality2...]"
                + "\n > add-role nameRole categoryName \n > login idUser");
        return map;
    }

    private Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> gestoreRisorse.getRisorse().get(User.class) + "");
        map.put("projects", (s) -> gestoreRisorse.getRisorse().get(Project.class) + "");
        map.put("categories", (s) -> gestoreRisorse.getRisorse().get(Category.class) + "");
        map.put("roles", (s) -> gestoreRisorse.getRisorse().get(Class.class) + "");
        return map;
    }

    private Map<String, Function<String[], String>> projectProposerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerInsertProposal c1 = new ControllerInsertProposal();
        c1.setUser(this.user);
        map.put("create-project", s -> consumerException(() -> {
            Project p = c1.createProject(Integer.parseInt(s[1]), s[2], s[3],
                    gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[4])));
            gestoreRisorse.getRisorse().get(Project.class).add(p);
        }));
        map.put("help", (s) -> " > create idProject nameProject descriptionProject categoryProject");
        return map;
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerAddPR c1 = new ControllerAddPR();
        c1.setUser(this.user);
        map.put("send-pr", s -> consumerException(() -> {
            c1.addPartecipationRequest(
                    gestoreRisorse.searchOne(Project.class, c -> c.getId() == Integer.parseInt(s[1])));
        }));
        map.put("help", (s) -> " > send-pr idProject");
        return map;
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerChooseProgramManager c1 = new ControllerChooseProgramManager();
        ControllerChooseTeamMembers c2 = new ControllerChooseTeamMembers();
        c1.setUser(this.user);
        c2.setUser(this.user);
        map.put("init-team", s -> consumerException(() -> {
            c1.initTeam(gestoreRisorse.searchOne(Project.class, c -> c.getId() == Integer.parseInt(s[1])));
        }));
        map.put("remove-pr", s -> consumerException(() -> {
            c1.removePartecipationRequest(searchPR(Integer.parseInt(s[1])),
                    List.of(s).subList(2, s.length).stream().reduce((x, y) -> x + y).get());
        }));
        map.put("add-designer", s -> consumerException(() -> {
            c2.addDesigner(searchPR(Integer.parseInt(s[1])));
        }));
        map.put("help", (s) -> " > init-team idProject"
                + "\n > add-designer idDesigner \n > remove-pr idDesigner reason");
        return map;
    }

    private PartecipationRequest searchPR(int id) {
        return ((Set<Project>) gestoreRisorse.getRisorse().get(Project.class)).stream()
                .map(p -> p.getTeam().getPartecipationRequests()).reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                }).get().stream().filter(x -> x.getDesigner().getId() == id).findAny().get();
    }

    private String consumerException(ConsumerException ce) {
        try {
            ce.accept();
            return "Success";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
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

}
