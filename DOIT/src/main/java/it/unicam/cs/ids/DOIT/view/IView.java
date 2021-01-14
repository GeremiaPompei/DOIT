package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.IController;

public interface IView {
    void start(IController controller);

    void stop();
}
