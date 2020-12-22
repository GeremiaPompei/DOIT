package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.ControllerInsertProposal;
import it.unicam.cs.ids.DOIT.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ControllerView {

    private User user;
    private Map<String, Map<String, Function<String[], String>>> commands;
    private GestoreRisorse gestoreRisorse;

    public ControllerView() {
        gestoreRisorse = new GestoreRisorse();
        commands = new HashMap<>();
        commands.put("user", userMap());
        commands.put("list", listMap());
    }

    public Map<String, Map<String, Function<String[], String>>> getCommands() {
        return commands;
    }

    private Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", (s) -> consumerException((u) ->
                gestoreRisorse.getRisorse().get(User.class)
                        .add(new User(Integer.parseInt(s[1]), s[2], s[3], List.of(s).subList(4, s.length)))));
        map.put("add-role", (s) -> consumerException((u) -> {
            user.addRole((Class<? extends Role>) Class.forName(
                    gestoreRisorse.searchOne(Class.class, r -> r.getSimpleName().equalsIgnoreCase(s[1])).getName()),
                    gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[2])));
            switch (s[1].toLowerCase()) {
                case "projectproposerrole":
                    commands.put("project-proposer", projectProposerMap());
                    break;
                case "designerrole":
                    commands.put("designer", designerMap());
                    break;
                case "programmanagerrole":
                    commands.put("program-manager", programManagerMap());
                    break;
            }
        }));
        map.put("login", (s) -> consumerException((o) -> {
            user = gestoreRisorse.searchOne(User.class, u -> u.getId() == Integer.parseInt(s[1]));
            if (user == null)
                throw new Exception("L'utente non esiste.");
        }));
        return map;
    }

    private Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> gestoreRisorse.getRisorse().get(User.class) + "");
        map.put("projects", (s) -> gestoreRisorse.getRisorse().get(Project.class) + "");
        map.put("categories", (s) -> gestoreRisorse.getRisorse().get(Category.class) + "");
        map.put("roles", (s) -> gestoreRisorse.getRisorse().get(Role.class) + "");
        return map;
    }

    private Map<String, Function<String[], String>> projectProposerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerInsertProposal c1 = new ControllerInsertProposal();
        c1.setUser(this.user);
        map.put("create-project", s -> consumerException(u -> {
            Project p = c1.createProject(Integer.parseInt(s[1]), s[2], s[3],
                    gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[4])));
            gestoreRisorse.getRisorse().get(Project.class).add(p);
        }));
        // TODO aggiungere comandi
        return map;
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        // TODO aggiungere comandi
        return map;
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        // TODO aggiungere comandi
        return map;
    }

    private String consumerException(ConsumerException<User> ce) {
        try {
            ce.accept(user);
            return "Success";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
