package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.role.IUser;

interface IControllerUser {
    void login(String email, String password);
    void signIn(String name, String surname, String birthDate, String sex, String email, String password);
    void logOut();
    IUser getUser();
}
