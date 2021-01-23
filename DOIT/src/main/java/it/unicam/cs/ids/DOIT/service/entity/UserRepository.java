package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.user.IUser;
import it.unicam.cs.ids.DOIT.user.TokenHandler;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<IUser, Long> {
    private Statement statement;
    private String name = "USER";

    public UserRepository(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void save(IUser userEntit) {
        try {
            statement.executeUpdate("INSERT INTO " + this.name + "(ID, NAME, SURNAME, BIRTH_DATE, SEX, EMAIL, PASSWORD, " +
                    "ROLES, TOKEN_DATE, TOKEN_ID) VALUES (" + userEntit.getId() + ", '" + userEntit.getName() +
                    "', '" + userEntit.getSurname() + "', '" + userEntit.getBirthDate() + "', '" + userEntit.getSex() + "', '" +
                    userEntit.getEmail() + "', '" + userEntit.getPassword() + "', '" + userEntit.getRoles() + "', '" +
                    userEntit.getToken().getDate() + "', " + userEntit.getToken().getToken() + ")");
        } catch (SQLException throwables) {

        }
    }

    @Override
    public void delete(Long aLongy) {
        try {
            statement.executeUpdate("DELETE FROM " + this.name + " WHERE ID = " + aLongy);
        } catch (SQLException throwables) {
        }
    }

    @Override
    public void update(IUser user) {
        delete(user.getId());
        save(user);
    }

    @Override
    public IUser findById(Long aLong) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + this.name + " WHERE ID = " + aLong);
            resultSet.next();
            IUser user = new User(resultSet.getLong("ID"), resultSet.getString("NAME"),
                    resultSet.getString("SURNAME"), resultSet.getString("BIRTH_DATE"),
                    resultSet.getString("SEX"), resultSet.getString("EMAIL"),
                    resultSet.getString("PASSWORD"));
            user.setToken(new TokenHandler(LocalDateTime.parse(resultSet.getString("TOKEN_DATE")),
                    resultSet.getLong("TOKEN_ID")));
            return user;
        } catch (SQLException throwables) {
            return null;
        }
    }

    @Override
    public List<IUser> findAll() {
        List<IUser> users = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + this.name);
            while (resultSet.next()) {
                IUser user = new User(resultSet.getLong("ID"), resultSet.getString("NAME"),
                        resultSet.getString("SURNAME"), resultSet.getString("BIRTH_DATE"),
                        resultSet.getString("SEX"), resultSet.getString("EMAIL"),
                        resultSet.getString("PASSWORD"));
                user.setToken(new TokenHandler(LocalDateTime.parse(resultSet.getString("TOKEN_DATE")),
                        resultSet.getLong("TOKEN_ID")));
                users.add(user);
            }
        } catch (SQLException throwables) {
        }
        return users;
    }

    @Entity
    @Table(name = "USER")
    private class UserEntity {
        @Id
        private Long id;
        private String name;
        private String surname;
        private String birthDate;
        private String sex;
        private String email;
        private String password;
        private String roles;
        private String tokenDate;
        private Long tokenId;
    }
}

