package it.unicam.cs.ids.DOIT.model.user;

import it.unicam.cs.ids.DOIT.model.role.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Column(name = "ID_User")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String birthDate;
    private String sex;
    private String email;
    private String password;
    @JoinColumn(name = "ID_RolesHandler")
    @OneToOne(cascade = CascadeType.ALL)
    private RolesHandler rolesHandler;
    @JoinColumn(name = "ID_TokenHandler")
    @OneToOne(cascade = CascadeType.ALL)
    private TokenHandler tokenHandler;

    public User(String name, String surname, String birthDate, String sex, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.rolesHandler = new RolesHandler(this);
        this.tokenHandler = new TokenHandler();
    }

    // Per Test
    public User(Long id, String name, String surname, String birthDate, String sex, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.rolesHandler = new RolesHandler(this);
        this.tokenHandler = new TokenHandler();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password))
            throw new NullPointerException("Password errata!");
    }

    public TokenHandler tokenHandlerGet() {
        return tokenHandler;
    }

    public RolesHandler getRolesHandler(Long token) {
        this.tokenHandlerGet().checkToken(token);
        return rolesHandler;
    }

    public RolesHandler rolesHandlerGet() {
        return rolesHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}