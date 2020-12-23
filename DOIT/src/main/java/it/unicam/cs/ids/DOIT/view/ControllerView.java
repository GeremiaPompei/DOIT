package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.*;
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
            int id = Integer.parseInt(s[1]);
            User usr = gestoreRisorse.searchOne(User.class, u -> u.getId() == id);
            if (usr != null)
                throw new Exception("Esiste gia un user con stesso id!");
            gestoreRisorse.getRisorse().get(User.class)
                    .add(new User(id, s[2], s[3], List.of(s).subList(4, s.length)));
        }));
        map.put("add-role", (s) -> consumerException(() -> {
            Category cat = gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[2]));
            if (cat == null)
                throw new Exception("Categoria inesistente!");
            user.addRole((Class<? extends Role>) Class.forName(
                    gestoreRisorse.searchOne(Class.class, r -> r.getSimpleName().equalsIgnoreCase(s[1])).getName()), cat);
            loadCommands();
        }));
        map.put("login", (s) -> consumerException(() -> {
            User usr = gestoreRisorse.searchOne(User.class, u -> u.getId() == Integer.parseInt(s[1]));
            if (usr == null)
                throw new Exception("L'utente non esiste!");
            this.user = usr;
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
        ControllerChooseProgramManager c2 = new ControllerChooseProgramManager();
        c1.setUser(this.user);
        map.put("create", s -> consumerException(() -> {
            int id = Integer.parseInt(s[1]);
            Project p = gestoreRisorse.searchOne(Project.class, pj -> pj.getId() == id);
            if (p != null)
                throw new Exception("Esiste gia un progetto con stesso id!");
            Category cat = gestoreRisorse.searchOne(Category.class, c -> c.getName().equalsIgnoreCase(s[4]));
            if (cat == null)
                throw new Exception("Categoria inesistente!");
            gestoreRisorse.getRisorse().get(Project.class).add(c1.createProject(id, s[2], s[3], cat));
        }));
        map.put("choose-pgm", s -> consumerException(() -> {
            Project p = gestoreRisorse.searchOne(Project.class, c -> c.getId() == Integer.parseInt(s[2]));
            if (p.getTeam() != null)
                throw new Exception("Team gia inizializzato!");
            int id = Integer.parseInt(s[1]);
            User usr = gestoreRisorse.searchOne(User.class, u -> u.getId() == id);
            if (usr == null)
                throw new Exception("Non esiste l'utente con id: [" + id + "]");
            c2.initTeam(usr, p);
        }));
        map.put("list-pgm", s -> gestoreRisorse.search(User.class, (u) -> {
            try {
                return c2.findProgramManagerList(gestoreRisorse.searchOne(Category.class, c -> c.getName()
                        .equalsIgnoreCase(s[1]))).apply(u);
            } catch (Exception e) {
                return false;
            }
        }).toString());
        map.put("help", (s) -> " > create idProject nameProject descriptionProject categoryProject\n > choose-pgm " +
                "idUser idProject\n > list-pgm nameCategory");
        return map;
    }

    private Map<String, Function<String[], String>> designerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerAddPR c1 = new ControllerAddPR();
        c1.setUser(this.user);
        map.put("send-pr", s -> consumerException(() -> {
            int id = Integer.parseInt(s[1]);
            c1.addPartecipationRequest(
                    gestoreRisorse.searchOne(Project.class, (p) -> {
                        try {
                            return c1.getProject(id).apply(p);
                        } catch (Exception e) {
                            return false;
                        }
                    }));
        }));
        map.put("list-pr", s -> gestoreRisorse.search(Project.class, p -> {
            try {
                return c1.getProjects(gestoreRisorse.searchOne(Category.class, c -> c.getName()
                        .equalsIgnoreCase(s[1]))).apply(p);
            } catch (Exception e) {
                return false;
            }
        }).toString());
        map.put("help", (s) -> " > send-pr idProject");
        return map;
    }

    private Map<String, Function<String[], String>> programManagerMap() {
        Map<String, Function<String[], String>> map = new HashMap<>();
        ControllerChooseProgramManager c1 = new ControllerChooseProgramManager();
        ControllerChooseTeamMembers c2 = new ControllerChooseTeamMembers();
        ControllerChooseProjectManager c3 = new ControllerChooseProjectManager();
        c1.setUser(this.user);
        c2.setUser(this.user);
        c3.setUser(this.user);
        map.put("remove-pr", s -> consumerException(() -> {
            PartecipationRequest pr = searchPR(Integer.parseInt(s[1]));
            if (pr == null)
                throw new Exception("Partecipation request non esistente!");
            c2.removePartecipationRequest(pr, List.of(s).subList(2, s.length).stream().reduce((x, y) -> x + y).get());
        }));
        map.put("add-designer", s -> consumerException(() -> {
            PartecipationRequest pr = searchPR(Integer.parseInt(s[1]));
            if (pr == null)
                throw new Exception("Partecipation request non esistente!");
            c2.addDesigner(pr);
        }));
        map.put("choose-pjm", s -> consumerException(() -> {
            int idU = Integer.parseInt(s[1]);
            int idP = Integer.parseInt(s[2]);
            c3.becomeProjectManager(gestoreRisorse.searchOne(User.class, u -> u.getId() == idU),
                    gestoreRisorse.searchOne(Project.class, u -> u.getId() == idP));
        }));
        map.put("help", (s) -> " > add-designer idDesigner \n > remove-pr idDesigner reason \n choose-pjm idDesigner idProject");
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
