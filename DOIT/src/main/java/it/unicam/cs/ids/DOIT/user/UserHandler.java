package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.stereotype.Service;

@Service
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
    public void logOut(int idUser, int token) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(idUser);
        user.getToken().clearToken();
    }

    @Override
    public IUser getUser(int idUser, int token) {
        IUser user = ServicesHandler.getInstance().getResourceHandler().getUser(idUser);
        if (user == null) return null;
        user.getToken().checkToken(token);
        return user;
    }

    public IUser getUser() {
        return null;
    }
}
