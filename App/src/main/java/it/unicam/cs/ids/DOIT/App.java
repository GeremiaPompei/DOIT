package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class App {

    public static void main(String [] args) {
        try {
            User user1 = new User(0, "Nome1", "Cognome1", new ArrayList<String>());
            user1.getGestoreRuoli().initProjectProposer();

            User user2 = new User(0, "Nome2", "Cognome2", new ArrayList<String>());
            user2.getGestoreRuoli().initDesigner(List.of("Notizia1", "Notizia2"));

            Project project = user1.getGestoreRuoli().getProjectProposer()
                    .createProject(0, "Nome", "Description");
            project.setProjectManager(user2);
            DB.TOTAL_USERS.add(user1);
            Function<User, IRole> func = u -> {
                ProgramManager pm = null;
                try {
                    pm = u.getGestoreRuoli().getProgramManager();
                } catch (RoleException e) {
                    e.printStackTrace();
                }
                return pm;
            };
            UserSearch.getIstance()
                    .SearchUser(func, new Category())
                    .forEach(u -> System.out.println(u.getName()));
            project.setProjectManager(user1);
            user1.getGestoreRuoli().getProjectManager().changeProjectState(project, ProjectState.INITIAL);
            List<PartecipationRequest> prl =
                    user1.getGestoreRuoli().getProgramManager().getPartecipationRequest(project.getTeam());
            user1.getGestoreRuoli().getProgramManager().remove(prl.get(0), "Perche no");
        } catch (RoleException e) {

        } catch (Exception e1) {

        }
    }
}
