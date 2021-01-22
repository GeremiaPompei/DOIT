package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import it.unicam.cs.ids.DOIT.user.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
public class UserEntity implements ResourceEntity<IUser> {
    @Id
    private Long id;
    @Transient
    @Autowired
    private ServicesHandler servicesHandler;
    private String name;
    private String surname;
    private String birthDate;
    private String sex;
    private String email;
    private String password;
    private String roles;
    private String token;

    @Override
    public void fromObject(IUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.birthDate = user.getBirthDate();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.token = user.getToken().getDate() + " " + user.getToken().getToken();
        try {
            this.roles = user.getRoles().stream().map(r -> r.getClass().getSimpleName()).reduce((x, y) -> x + " " + y).get();
        } catch (Exception e) { }
    }

    @Override
    public IUser toObject() throws Exception {
        IUser user = servicesHandler.getFactoryModel().createUser(this.name, this.surname, this.birthDate, this.sex, this.email, this.password);
        String[] params = token.split(" ");
        user.setToken(new TokenHandler(LocalDateTime.parse(params[0]), Long.parseLong(params[1])));
        return user;
    }
}
