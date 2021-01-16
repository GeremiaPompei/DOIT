package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.controller.ControllerUser;
import it.unicam.cs.ids.DOIT.controller.IControllerUser;
import it.unicam.cs.ids.DOIT.view.IView;
import it.unicam.cs.ids.DOIT.view.MainView;

public class App {

    private IView view;
    private IControllerUser controller;

    private App(IView view, IControllerUser controller) {
        this.view = view;
        this.controller = controller;
    }

    private void run() {
        view.start(controller);
        view.stop();
    }

    public static void main(String[] args) {
        createAppSimple().run();
    }

    private static App createAppSimple() {
        IControllerUser controller = new ControllerUser();
        IView view = new MainView();
        return new App(view, controller);
    }
}
