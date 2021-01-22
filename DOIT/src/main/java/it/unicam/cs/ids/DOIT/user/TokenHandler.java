package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.time.LocalDateTime;

public class TokenHandler {
    private LocalDateTime date;
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
        if (LocalDateTime.now().isAfter(date.plusDays(1)))
            throw new IllegalArgumentException("Token scaduto, riautenticati!");
    }

    public Long generateToken() {
        date = LocalDateTime.now();
        token = ServicesHandler.getInstance().getIdGenerator().getId();
        return token;
    }

    @Override
    public String toString() {
        return date + " - " + token;
    }

    public void clearToken() {
        token = -1L;
        date = LocalDateTime.MIN;
    }
}
