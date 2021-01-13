package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.simple.controller.Controller;
import it.unicam.cs.ids.DOIT.domain.controller.IController;
import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.domain.model.IFactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
import it.unicam.cs.ids.DOIT.domain.view.IView;
import it.unicam.cs.ids.DOIT.simple.view.MainView;

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
        IResourceHandler resourceHandler = new ResourceHandler();
        IFactoryModel factory = new FactoryModel(resourceHandler);
        IController controller = new Controller(factory, resourceHandler);
        IView view = new MainView();
        return new App(view, controller);
    }
}
