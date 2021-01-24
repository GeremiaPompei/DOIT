package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.user.User;

public interface IUserHandler {
    User logIn(String email, String password);

    void signIn(String name, String surname, String birthDate, String sex, String email, String password);

    void logOut(Long idUser, Long token);

    User getUser(Long idUser, Long token);
}
