package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.controller.Controller;
import it.unicam.cs.ids.DOIT.controller.IController;
import it.unicam.cs.ids.DOIT.model.FactoryModel;
import it.unicam.cs.ids.DOIT.model.IFactoryModel;
import it.unicam.cs.ids.DOIT.view.IView;
import it.unicam.cs.ids.DOIT.view.MainView;

public class App {

    public static void main(String[] args) {
        IFactoryModel factory = new FactoryModel();
        IController controller = new Controller(factory);
        IView view = new MainView(controller);
        view.start();
        view.stop();
    }
}
