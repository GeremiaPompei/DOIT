package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.view.IView;

public class App {
    public static void main(String[] args) {
        IView view;
        if (args == null || args.length < 2)
            view = new SpringBootServer(new String[]{"userHandler", "search"});
        else
            view = new SpringBootServer(args);
        view.start();
        view.stop();

    }
}
