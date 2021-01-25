package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class UserMVC {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

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

    public User logIn(String email, String password) {
        User user = userHandler.logIn(findByEmail(email), password);
        userRepository.save(user);
        return user;
    }

    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        User user = findByEmail(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        user = userHandler.signIn(name, surname, birthDate, sex, email, password);
        userRepository.save(user);
    }

    public void logOut(Long idUser, Long token) {
        User user = userRepository.findById(idUser).orElse(null);
        userHandler.logOut(user);
        userRepository.save(user);
    }

    public User getUser(Long idUser, Long token) {
        User user = userRepository.findById(idUser).orElse(null);
        return userHandler.getUser(user, token);
    }

    public String addRole(Long idUser, Long tokenUser, String idRole, String idCategory) {
        Category category = categoryRepository.findById(idCategory).orElse(null);
        User user = this.getUser(idUser, tokenUser);
        switch (idRole) {
            case "project-proposer":
                user.getRolesHandler().addProjectProposerRole(category);
                break;
            case "program-manager":
                user.getRolesHandler().addProgramManagerRole(category);
                break;
            case "designer":
                user.getRolesHandler().addDesignerRole(category);
                break;
            case "project-manager":
                user.getRolesHandler().addProjectManagerRole(category);
                break;
        }
        userRepository.save(user);
        return "success";
    }

    public String removeRole(Long idUser, Long tokenUser, String idRole) {
        User user = this.getUser(idUser, tokenUser);
        switch (idRole) {
            case "project-proposer":
                user.getRolesHandler().removeProjectProposerRole();
                break;
            case "program-manager":
                user.getRolesHandler().removeProgramManagerRole();
                break;
            case "designer":
                user.getRolesHandler().removeDesignerRole();
                break;
            case "project-manager":
                user.getRolesHandler().removeProjectManagerRole();
                break;
        }
        userRepository.save(user);
        return "success";
    }
}
