package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userHandler")
public class UserHandler implements IUserHandler {
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    @Override
    public IUser logIn(String email, String password) {
        IUser user = servicesHandler.getResourceHandler().getUser(email);
        if (user == null)
            throw new NullPointerException("Utente non trovato!");
        if (!user.getPassword().equals(password))
            throw new NullPointerException("Password errata!");
        user.getToken().generateToken();
        servicesHandler.getResourceHandler().insert(user);
        return user;
    }

    @Override
    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        IUser user = servicesHandler.getResourceHandler().getUser(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        servicesHandler.getFactoryModel().createUser(name, surname, birthDate, sex, email, password);
    }

    @Override
    public void logOut(Long idUser, Long token) {
        IUser user = servicesHandler.getResourceHandler().getUser(idUser);
        servicesHandler.getResourceHandler().insert(user);
        user.getToken().clearToken();
    }

    @Override
    public IUser getUser(Long idUser, Long token) {
        IUser user = servicesHandler.getResourceHandler().getUser(idUser);
        if (user == null) return null;
        user.getToken().checkToken(token);
        servicesHandler.getResourceHandler().insert(user);
        return user;
    }
}
