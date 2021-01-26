package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.model.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private RepositoryHandler repositoryHandler;

    private User findByEmail(String email) {
        Iterator<User> iterator = repositoryHandler.getUserRepository().findAll().iterator();
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
        repositoryHandler.getUserRepository().save(user);
        return user;
    }

    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        User user = findByEmail(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        user = userHandler.signIn(name, surname, birthDate, sex, email, password);
        repositoryHandler.getUserRepository().save(user);
    }

    public void logOut(Long idUser, Long token) {
        User user = repositoryHandler.getUserRepository().findById(idUser).orElse(null);
        userHandler.logOut(user);
        repositoryHandler.getUserRepository().save(user);
    }

    public User getUser(Long idUser, Long token) {
        User user = repositoryHandler.getUserRepository().findById(idUser).orElse(null);
        return userHandler.getUser(user, token);
    }

    public String addRole(Long idUser, Long tokenUser, String idRole, String idCategory) {
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).orElse(null);
        User user = this.getUser(idUser, tokenUser);
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler().addProjectProposerRole(category);
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler().addProgramManagerRole(category);
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler().addDesignerRole(category);
        else if (idRole.equals(getRoles().get(3)))
            throw new IllegalArgumentException("Non può essere aggiunto il ruolo di projectManager!");
        repositoryHandler.getUserRepository().save(user);
        return "success";
    }

    public String removeRole(Long idUser, Long tokenUser, String idRole) {
        User user = this.getUser(idUser, tokenUser);
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler().removeProjectProposerRole();
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler().removeProgramManagerRole();
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler().removeDesignerRole();
        else if (idRole.equals(getRoles().get(3)))
            user.getRolesHandler().removeProjectManagerRole();
        repositoryHandler.getUserRepository().save(user);
        return "success";
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("project-proposer");
        roles.add("program-manager");
        roles.add("designer");
        roles.add("project-manager");
        return roles;
    }
}
