package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.role.*;
import it.unicam.cs.ids.DOIT.model.user.TokenHandler;
import it.unicam.cs.ids.DOIT.repository.RepositoryHandler;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

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
        RolesHandler rh = repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser);
        if(rh.getHandyRoles().contains(idRole))
            throw new IllegalArgumentException("Non può essere aggiunto questo ruolo!");
        rh.addRole(idRole, category);
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeRole(Long idUser, Long tokenUser, String idRole) {
        User user = this.getUser(idUser, tokenUser);
        repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser).removeRole(idRole);
        repositoryHandler.getUserRepository().save(user);
    }

    public void addCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser).getHandyRoles()
                .stream().filter(r -> r.getType().equals(idRole)).findAny().get().addCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

    public void removeCategory(Long idUser, Long tokenUser, String idRole, String idCategory) {
        User user = this.getUser(idUser, tokenUser);
        Category category = repositoryHandler.getCategoryRepository().findById(idCategory).get();
        repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser).getHandyRoles()
                .stream().filter(r -> r.getType().equals(idRole)).findAny().get().removeCategory(category);
        repositoryHandler.getUserRepository().save(user);
    }

    public List<String> getMyRolesType(Long idUser, Long tokenUser) {
        return repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser).getRoles()
                .stream()
                .map(r -> r.getType()).collect(Collectors.toList());
    }

    public RolesHandler getMyRoles(Long idUser, Long tokenUser) {
        return repositoryHandler.getUserRepository().findById(idUser).get().getRolesHandler(tokenUser);
    }

    public List<String> getRolesType() {
        List<String> roles = new ArrayList<>();
        roles.add(ProjectProposerRole.TYPE);
        roles.add(ProgramManagerRole.TYPE);
        roles.add(DesignerRole.TYPE);
        roles.add(ProjectManagerRole.TYPE);
        return roles;
    }

    public void sendEmail(Long idUser, Long tokenUser, String mex) throws MessagingException {
        User user = repositoryHandler.getUserRepository().findById(idUser).get();
        user.tokenHandlerGet().checkToken(tokenUser);
        Properties properties = new Properties();
        String myEmail = "doit.unicam@gmail.com";
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", myEmail);
        Session session = Session.getDefaultInstance(properties);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(myEmail));
        InternetAddress[] toAddresses = {new InternetAddress(myEmail)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject("DOIT request");
        msg.setSentDate(new Date());
        msg.setText("by " + user.getName() + " " + user.getSurname() + ": " + user.getEmail() + "\n\n" + mex);
        Transport t = session.getTransport("smtp");
        t.connect(myEmail, "ollarethegang30");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }
}
