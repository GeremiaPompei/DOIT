package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import it.unicam.cs.ids.DOIT.user.TokenHandler;
import it.unicam.cs.ids.DOIT.user.User;
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
    private Long tokenId;
    private String tokenDate;

    @Override
    public void fromObject(IUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.birthDate = user.getBirthDate();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.tokenDate = user.getToken().getDate().toString();
        this.tokenId = user.getToken().getToken();
        try {
            this.roles = user.getRoles().stream().map(r -> r.getClass().getSimpleName()).reduce((x, y) -> x + " " + y).get();
        } catch (Exception e) {
        }
    }

    @Override
    public IUser toObject() throws Exception {
        IUser user = new User(this.id, this.name, this.surname, this.birthDate, this.sex, this.email, this.password);
        user.setToken(new TokenHandler(LocalDateTime.parse(this.tokenDate), this.tokenId));
        return user;
    }
}
