package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class User {
    @Transient
    private ServicesHandler servicesHandler = ServicesHandler.getInstance();

    @Id
    @Column(name = "ID_User")
    private Long id;
    private String name;
    private String surname;
    private String birthDate;
    private String sex;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<Role> roles;
    @JoinColumn(name = "ID_TokenHandler")
    @OneToOne(cascade = CascadeType.ALL)
    private TokenHandler token;

    public User(Long id, String name, String surname, String birthDate, String sex, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
        this.token = new TokenHandler();
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

    public String getPassword() {
        return password;
    }

    public TokenHandler getToken() {
        return token;
    }

    public <T extends Role> boolean addRole(Class<T> clazz, String idCategory)
            throws ReflectiveOperationException {
        Category category = servicesHandler.getResourceHandler().getCategory(idCategory);
        if (category == null)
            throw new NullPointerException();
        return this.roles.add(servicesHandler.getFactoryModel().createRole(clazz, this, category));
    }

    public <T extends Role> T getRole(Class<T> clazz) throws RoleException {
        return clazz.cast(this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElseThrow(RoleException::new));
    }

    public <T extends Role> boolean removeRole(Class<T> clazz) throws RoleException {
        if (!this.getRole(clazz).getTeams().isEmpty())
            throw new IllegalArgumentException("Impossibile eliminare un ruolo se contiene team in esecuzione!");
        Role role = this.roles
                .stream()
                .filter(clazz::isInstance)
                .findAny()
                .orElse(null);
        if (!role.getTeams().isEmpty())
            throw new IllegalArgumentException("Bisogna non possedere alcun progetto nel ruolo da eliminare!");
        return this.roles.remove(role);
    }

    public <T extends Role> boolean addRole(String idRole, String idCategory) throws ReflectiveOperationException {
        Class clazz = Class.forName(servicesHandler.getResourceHandler().getRolesByName(idRole));
        return addRole(clazz, idCategory);
    }

    public <T extends Role> T getRole(String idRole) throws RoleException, ClassNotFoundException {
        Class clazz = Class.forName(servicesHandler.getResourceHandler().getRolesByName(idRole));
        return (T) getRole(clazz);
    }

    public <T extends Role> boolean removeRole(String idRole) throws ClassNotFoundException, RoleException {
        Class clazz = Class.forName(servicesHandler.getResourceHandler().getRolesByName(idRole));
        return removeRole(clazz);
    }

    public Set<Role> getRoles() {
        return roles;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token=" + token +
                ", roles=" + roles +
                '}';
    }

    public void setToken(TokenHandler token) {
        this.token = token;
    }
}