package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String [] args) {
        User user1 = new User(0,"Nome1","Cognome1",new ArrayList<String>());
        user1.getGestoreRuoli().initProjectProposer();

        User user2 = new User(0,"Nome2","Cognome2",new ArrayList<String>());
        user2.getGestoreRuoli().initDesigner(List.of("Notizia1", "Notizia2"));

        Project project = user1.getGestoreRuoli().getProjectProposer()
                .createProject(0,"Nome","Description");
        project.setProjectManager(user2);
        DB.TOTAL_USERS.add(user1);
        UserSearch.getIstance()
                .SearchUser(u -> u.getGestoreRuoli().getProgramManager(), new Category())
                .forEach(u -> System.out.println(u.getName()));
        project.setProjectManager(user1);
        try {
            user1.getGestoreRuoli().getProjectManager().changeProjectState(project, ProjectState.INITIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PartecipationRequest> prl =
                user1.getGestoreRuoli().getProgramManager().getPartecipationRequest(project.getTeam());
        user1.getGestoreRuoli().getProgramManager().remove(prl.get(0),"Perche no");
    }
}
