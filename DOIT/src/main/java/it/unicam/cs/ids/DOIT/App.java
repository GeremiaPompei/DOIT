package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.controller.Controller;
import it.unicam.cs.ids.DOIT.controller.IController;
import it.unicam.cs.ids.DOIT.model.Factory;
import it.unicam.cs.ids.DOIT.model.IFactory;
import it.unicam.cs.ids.DOIT.model.IResourceHandler;
import it.unicam.cs.ids.DOIT.model.ResourceHandler;
import it.unicam.cs.ids.DOIT.view.IView;
import it.unicam.cs.ids.DOIT.view.MainView;

public class App {

    public static void main(String[] args) {
        IResourceHandler resourceHandler = new ResourceHandler();
        IFactory factory = new Factory(resourceHandler);
        IController controller = new Controller(factory, resourceHandler);
        IView view = new MainView(controller);
        view.start();
        view.stop();
    }
}
