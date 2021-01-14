package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.controller.Controller;
import it.unicam.cs.ids.DOIT.controller.IController;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.view.IView;
import it.unicam.cs.ids.DOIT.view.MainView;

public class App {

    private IView view;
    private IController controller;

    private App(IView view, IController controller) {
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
        IController controller = new Controller();
        IView view = new MainView();
        return new App(view, controller);
    }
}
