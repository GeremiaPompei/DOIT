package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.ProjectProposer;
import it.unicam.cs.ids.DOIT.model.RoleException;
import it.unicam.cs.ids.DOIT.model.User;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        User user1 = new User(6, "Nome", "Cognome", new ArrayList<>());
        Object[] params = {user1};
        try {
            user1.addRole(ProjectProposer.class, params, User.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        try {
            user1.getRole(ProjectProposer.class);
        } catch (RoleException e) {
            System.err.println(e.getMessage());
        }
    }
}
