package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.model.RoleException;
import it.unicam.cs.ids.DOIT.model.User;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        User user1 = new User(6, "Nome", "Cognome", new ArrayList<>());
        Object[] params = {user1};
        Class<?>[] classes = {User.class};
        try {
            user1.addRole(ProjectProposerRole.class, params, classes);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        try {
            user1.getRole(ProjectProposerRole.class);
        } catch (RoleException e) {
            System.err.println(e.getMessage());
        }
    }
}
