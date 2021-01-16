package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.user.IUserHandler;

public interface IView {
    void start(IUserHandler controller);

    void stop();
}
