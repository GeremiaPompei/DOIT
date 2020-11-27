package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String [] args) {
        User user1 = new User(0,"Nome1","Cognome1",new ArrayList<String>());
        user1.getGestoreRuoli().setProjectProposer(new ProjectProposer());

        User user2 = new User(0,"Nome2","Cognome2",new ArrayList<String>());
        user2.getGestoreRuoli().setDesigner(new Designer(List.of("Notizia1", "Notizia2")));

        Project project = user1.getGestoreRuoli().getProjectProposer()
                .createProject(0,"Nome","Description");
        project.setProjectManager(user2);
        DB.TOTAL_USERS.add(user1);
        UserSearch.getIstance()
                .SearchUser(u -> u.getGestoreRuoli().getProgramManager() != null, new Category())
                .forEach(u -> System.out.println(u.getName()));
    }
}
