package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
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
        User user = findByEmail(email);
        user.checkPassword(password);
        user.getTokenHandler().generateToken();
        repositoryHandler.getUserRepository().save(user);
        return user;
    }

    public void signIn(String name, String surname, String birthDate, String sex, String email, String password) {
        User user = findByEmail(email);
        if (user != null)
            throw new IllegalArgumentException("Email gia usata!");
        repositoryHandler.getUserRepository().save(new User(name, surname, birthDate, sex, email, password));
    }

    public void logOut(Long idUser, Long token) {
        User user = getUser(idUser, token);
        user.getTokenHandler().clearToken();
        repositoryHandler.getUserRepository().save(user);
    }

    public User getUser(Long idUser, Long token) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        user.getTokenHandler().checkToken(token);
        return user;
    }

    public void addRole(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler(tokenUser).addProjectProposerRole(category);
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler(tokenUser).addProgramManagerRole(category);
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler(tokenUser).addDesignerRole(category);
        else if (idRole.equals(getRoles().get(3)))
            throw new IllegalArgumentException("Non può essere aggiunto il ruolo di projectManager!");
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeRole(Long idUser, Long tokenUser, String idRole) {
        User user = this.getUser(idUser, tokenUser);
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler(tokenUser).removeProjectProposerRole();
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler(tokenUser).removeProgramManagerRole();
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler(tokenUser).removeDesignerRole();
        else if (idRole.equals(getRoles().get(3)))
            user.getRolesHandler(tokenUser).removeProjectManagerRole();
        repositoryHandler.getUserRepository().save(user);
    }

    public void addCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler(tokenUser).getProjectProposerRole().addCategory(category);
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler(tokenUser).getProgramManagerRole().addCategory(category);
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler(tokenUser).getDesignerRole().addCategory(category);
        else if (idRole.equals(getRoles().get(3)))
            throw new IllegalArgumentException("Non può essere aggiunta una categoria nel projectManager!");
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRoles().get(0)))
            user.getRolesHandler(tokenUser).getProjectProposerRole().removeCategory(category);
        else if (idRole.equals(getRoles().get(1)))
            user.getRolesHandler(tokenUser).getProgramManagerRole().removeCategory(category);
        else if (idRole.equals(getRoles().get(2)))
            user.getRolesHandler(tokenUser).getDesignerRole().removeCategory(category);
        else if (idRole.equals(getRoles().get(3)))
            throw new IllegalArgumentException("Non può essere rimossa una categoria nel projectManager!");
        repositoryHandler.getUserRepository().save(user);
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
