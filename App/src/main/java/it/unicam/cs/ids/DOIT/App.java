package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;

public class App {

    public static void main(String [] args) {
        User user1 = new User(0,"Nome1","Cognome1",new ArrayList<String>());
        user1.getGestoreRuoli().setProjectProposer(new ProjectProposer());

        User user2 = new User(0,"Nome2","Cognome2",new ArrayList<String>());
        user2.getGestoreRuoli().setDesigner(new Designer(new ArrayList<String>()));
        
        user2.getGestoreRuoli().getDesigner().getCv();

        Project project = user1.getGestoreRuoli().getProjectProposer()
                .createProject(0,"Nome","Description");
        project.setProjectManager(user2);
    }
}
