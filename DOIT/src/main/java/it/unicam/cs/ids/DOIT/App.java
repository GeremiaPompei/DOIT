package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.ProjectProposer;
import it.unicam.cs.ids.DOIT.model.User;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user1 = new User(6, "Nome", "Cognome", new ArrayList<>());
        user1.addRole(new ProjectProposer(user1));
        ProjectProposer pp = user1.getRole(ProjectProposer.class);
    }
}
