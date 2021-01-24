package it.unicam.cs.ids.DOIT.user;

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
    private Timestamp date;

    private Long token;

    public TokenHandler() {
        clearToken();
    }

    public Long getToken() {
        return token;
    }

    public void checkToken(Long token) {
        if (!this.token.equals(token))
            throw new IllegalArgumentException("Token errato, riautenticati!");
        if (LocalDateTime.now().isAfter(date.toLocalDateTime().plusDays(1)))
            throw new IllegalArgumentException("Token scaduto, riautenticati!");
    }

    public Long generateToken() {
        date = Timestamp.valueOf(LocalDateTime.now());
        token = (long) (100000 + new Random().nextInt(900000));
        return token;
    }

    @Override
    public String toString() {
        return date + " - " + token;
    }

    public void clearToken() {
        token = -1L;
        date = Timestamp.valueOf(LocalDateTime.MIN);
    }

    public LocalDateTime getDate() {
        return date.toLocalDateTime();
    }
}
