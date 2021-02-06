package it.unicam.cs.ids.DOIT.entity.user;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class TokenHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_TokenHandler")
    private Long id;
    private Timestamp dateTime;

    private Long token;

    public TokenHandler() {
        clearToken();
    }

    public Long getToken() {
        return token;
    }

    public void checkToken(Long token) {
        if (!this.token.equals(token))
            throw new IllegalArgumentException("Wrong token, re-authenticate!");
        if (LocalDateTime.now().isAfter(dateTime.toLocalDateTime().plusDays(1)))
            throw new IllegalArgumentException("Expired token, re-authenticate!");
    }

    public Long generateToken() {
        dateTime = Timestamp.valueOf(LocalDateTime.now());
        token = (long) (100000 + new Random().nextInt(900000));
        return token;
    }

    public void clearToken() {
        token = -1L;
        dateTime = Timestamp.valueOf(LocalDateTime.MIN);
    }

    public LocalDateTime getDateTime() {
        return dateTime.toLocalDateTime();
    }
}
