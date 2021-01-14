package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.role.IUser;

public class ControllerUser implements IControllerUser {

    private IUser user;

    @Override
    public void login(String email, String password) {

    }

    @Override
    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {

    }

    @Override
    public void logOut() {
        user = null;
    }

    @Override
    public IUser getUser() {
        return user;
    }
}
