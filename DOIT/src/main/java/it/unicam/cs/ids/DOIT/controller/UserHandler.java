package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.stereotype.Service;

@Service("userHandler")
public class UserHandler implements IUserHandler {

    @Override
    public IUser logIn(String email, String password) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(email);
        if (user == null)
            throw new NullPointerException("Utente non trovato!");
        if (!user.getPassword().equals(password))
            throw new NullPointerException("Password errata!");
        user.getToken().generateToken();
        return user;
    }

    @Override
    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        ServicesHandler.getInstance().getFactoryModel().createUser(name, surname, birthDate, sex, email, password);
    }

    @Override
    public void logOut(Long idUser, Long token) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(idUser);
        user.getToken().clearToken();
    }

    @Override
    public IUser getUser(Long idUser, Long token) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(idUser);
        if (user == null) return null;
        user.getToken().checkToken(token);
        return user;
    }
}