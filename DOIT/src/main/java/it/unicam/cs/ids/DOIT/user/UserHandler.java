package it.unicam.cs.ids.DOIT.user;

import org.springframework.stereotype.Service;

@Service
public class UserHandler {

    public User logIn(User user, String password) {
        if (user == null)
            throw new NullPointerException("Utente non trovato!");
        if (!user.getPassword().equals(password))
            throw new NullPointerException("Password errata!");
        user.getToken().generateToken();
        return user;
    }

    public User signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        return new User(name, surname, birthDate, sex, email, password);
    }

    public void logOut(User user) {
        user.getToken().clearToken();
    }

    public User getUser(User user, Long token) {
        user.getToken().checkToken(token);
        return user;
    }
}
