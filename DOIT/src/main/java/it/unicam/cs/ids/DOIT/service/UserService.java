package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.role.Role;
import it.unicam.cs.ids.DOIT.model.role.RolesHandler;
import it.unicam.cs.ids.DOIT.model.user.TokenHandler;
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
        user.tokenHandlerGet().generateToken();
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
        user.tokenHandlerGet().clearToken();
        repositoryHandler.getUserRepository().save(user);
    }

    public User getUser(Long idUser, Long token) {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        user.tokenHandlerGet().checkToken(token);
        return user;
    }

    public void addRole(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRolesType().get(0)))
            user.getRolesHandler(tokenUser).addProjectProposerRole(category);
        else if (idRole.equals(getRolesType().get(1)))
            user.getRolesHandler(tokenUser).addProgramManagerRole(category);
        else if (idRole.equals(getRolesType().get(2)))
            user.getRolesHandler(tokenUser).addDesignerRole(category);
        else if (idRole.equals(getRolesType().get(3)))
            throw new IllegalArgumentException("Non può essere aggiunto il ruolo di projectManager!");
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeRole(Long idUser, Long tokenUser, String idRole) {
        User user = this.getUser(idUser, tokenUser);
        if (idRole.equals(getRolesType().get(0)))
            user.getRolesHandler(tokenUser).removeProjectProposerRole();
        else if (idRole.equals(getRolesType().get(1)))
            user.getRolesHandler(tokenUser).removeProgramManagerRole();
        else if (idRole.equals(getRolesType().get(2)))
            user.getRolesHandler(tokenUser).removeDesignerRole();
        else if (idRole.equals(getRolesType().get(3)))
            user.getRolesHandler(tokenUser).removeProjectManagerRole();
        repositoryHandler.getUserRepository().save(user);
    }

    public void addCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRolesType().get(0)))
            user.getRolesHandler(tokenUser).getProjectProposerRole().addCategory(category);
        else if (idRole.equals(getRolesType().get(1)))
            user.getRolesHandler(tokenUser).getProgramManagerRole().addCategory(category);
        else if (idRole.equals(getRolesType().get(2)))
            user.getRolesHandler(tokenUser).getDesignerRole().addCategory(category);
        else if (idRole.equals(getRolesType().get(3)))
            throw new IllegalArgumentException("Non può essere aggiunta una categoria nel projectManager!");
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        if (idRole.equals(getRolesType().get(0)))
            user.getRolesHandler(tokenUser).getProjectProposerRole().removeCategory(category);
        else if (idRole.equals(getRolesType().get(1)))
            user.getRolesHandler(tokenUser).getProgramManagerRole().removeCategory(category);
        else if (idRole.equals(getRolesType().get(2)))
            user.getRolesHandler(tokenUser).getDesignerRole().removeCategory(category);
        else if (idRole.equals(getRolesType().get(3)))
            throw new IllegalArgumentException("Non può essere rimossa una categoria nel projectManager!");
        repositoryHandler.getUserRepository().save(user);
    }

    public List<String> getRolesType() {
        List<String> roles = new ArrayList<>();
        roles.add("project-proposer");
        roles.add("program-manager");
        roles.add("designer");
        roles.add("project-manager");
        return roles;
    }

    public List<String> getMyRolesType(Long idUser, Long tokenUser) {
        List<String> roles = new ArrayList<>();
        RolesHandler rh = repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser);
        if (rh.isProjectProposer())
            roles.add(getRolesType().get(0));
        if (rh.isProgramManager())
            roles.add(getRolesType().get(1));
        if (rh.isDesigner())
            roles.add(getRolesType().get(2));
        if (rh.isProjectManager())
            roles.add(getRolesType().get(3));
        return roles;
    }

    public RolesHandler getMyRoles(Long idUser, Long tokenUser) {
        return repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser);
    }
}
