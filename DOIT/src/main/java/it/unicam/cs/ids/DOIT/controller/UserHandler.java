package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.service.IdGenerator;
import it.unicam.cs.ids.DOIT.service.entity.UserRepository;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("userHandler")
public class UserHandler implements IUserHandler {

    private UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User findByEmail(String email) {
        Iterator<User> iterator = userRepository.findAll().iterator();
        User user = null;
        while (iterator.hasNext()) {
            user = iterator.next();
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    @Override
    public User logIn(String email, String password) {
        User user = findByEmail(email);
        if (user == null)
            throw new NullPointerException("Utente non trovato!");
        if (!user.getPassword().equals(password))
            throw new NullPointerException("Password errata!");
        user.getToken().generateToken();
        try {
            userRepository.delete(user);
        } catch (Exception e) {
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        User user = findByEmail(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        user = new User(IdGenerator.getId(), name, surname, birthDate, sex, email, password);
        userRepository.save(user);
    }

    @Override
    public void logOut(Long idUser, Long token) {
        User user = userRepository.findById(idUser).get();
        userRepository.save(user);
        user.getToken().clearToken();
    }

    @Override
    public User getUser(Long idUser, Long token) {
        User user = userRepository.findById(idUser).get();
        if (user == null) return null;
        user.getToken().checkToken(token);
        userRepository.save(user);
        return user;
    }
}
