package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.controller.IControllerUser;

public interface IView {
    void start(IControllerUser controller);

    void stop();
}
