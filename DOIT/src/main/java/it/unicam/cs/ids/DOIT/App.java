package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.*;
import it.unicam.cs.ids.DOIT.view.ConsumerException;
import it.unicam.cs.ids.DOIT.view.MainView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class App {

    private static User user;
    private static Map<String, Map<String, Function<String[], String>>> commands = new HashMap<>();

    public static void main(String[] args) {
        commands.put("user", userMap());
        commands.put("list", listMap());
        MainView view = new MainView(commands);
        view.start();
        view.stop();
    }

    private static Map<String, Function<String[], String>> userMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create", (s) -> consumer((o) ->
                Database.users.add(new User(Integer.parseInt(s[1]), s[2], s[3], List.of(s).subList(4, s.length)))));
        map.put("add-role", (s) -> consumer((o) -> {
            user.addRole(Database.searchRole(s[1]), Database.searchCategory(s[2]));
            switch (s[1]) {
                case "ProjectProposerRole":
                    commands.put("init-project", projectMap());
            }
        }));
        map.put("login", (s) -> consumer((o) -> user = Database.searchUser(Integer.parseInt(s[1]))));
        return map;
    }

    private static Map<String, Function<String[], String>> listMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("users", (s) -> Database.users + "");
        map.put("projects", (s) -> Database.projects + "");
        map.put("categories", (s) -> Database.categories + "");
        map.put("roles", (s) -> Database.roles + "");
        return map;
    }

    private static Map<String, Function<String[], String>> projectMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        map.put("create",
                (s) -> consumer(
                        (u) ->
                                Database.projects.add(user.getRole(ProjectProposerRole.class)
                                        .createProject(Integer.parseInt(s[1]), s[2], s[3],
                                                Database.searchCategory(s[4])))
                )
        );
        return map;
    }

    private static String consumer(ConsumerException<User> func) {
        try {
            func.accept(user);
            return "Success";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
