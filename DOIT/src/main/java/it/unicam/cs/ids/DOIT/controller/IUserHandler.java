package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.user.IUser;

public interface IUserHandler {
    IUser logIn(String email, String password);

    void signIn(String name, String surname, String birthDate, String sex, String email, String password);

    void logOut(Long idUser, Long token);

    IUser getUser(Long idUser, Long token);
}
