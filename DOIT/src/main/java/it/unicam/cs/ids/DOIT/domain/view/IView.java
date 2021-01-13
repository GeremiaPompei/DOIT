package it.unicam.cs.ids.DOIT.domain.view;

import it.unicam.cs.ids.DOIT.domain.controller.IController;

public interface IView {
    void start(IController controller);

    void stop();
}
