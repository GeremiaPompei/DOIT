package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.time.LocalDateTime;

public class TokenHandler {
    private LocalDateTime date;
    private int token;

    public TokenHandler() {
        clearToken();
    }

    public void checkToken(int token) {
        if (this.token != token || LocalDateTime.now().isAfter(date.plusDays(1)))
            throw new IllegalArgumentException("Token scaduto, riautenticati!");
    }

    public int generateToken() {
        date = LocalDateTime.now();
        token = ServicesHandler.getInstance().getIdGenerator().getId();
        return token;
    }

    @Override
    public String toString() {
        return date + " - " + token;
    }

    public void clearToken() {
        token = -1;
        date = LocalDateTime.MIN;
    }
}
