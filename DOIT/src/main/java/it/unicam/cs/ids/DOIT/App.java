package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.IUserHandler;
import it.unicam.cs.ids.DOIT.view.IView;
import it.unicam.cs.ids.DOIT.view.ConsoleView;

public class App {

    public static void main(String[] args) {
        IUserHandler controller = new UserHandler();
        IView view = new ConsoleView();
        view.start(controller);
        view.stop();
    }
}
