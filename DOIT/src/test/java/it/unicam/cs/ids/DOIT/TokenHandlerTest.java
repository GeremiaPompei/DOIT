package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.user.TokenHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TokenHandlerTest {

    TokenHandler tokenHandler;

    @BeforeEach
    void init() {
        tokenHandler = new TokenHandler();
    }

    @Test
    void getToken() {
        tokenHandler.generateToken();
        assertNotNull(tokenHandler.getToken());
    }

    @Test
    void checkToken() {
        tokenHandler.generateToken();
        assertDoesNotThrow(()->tokenHandler.checkToken(tokenHandler.getToken()));
    }

    @Test
    void generateToken() {
        assertEquals(tokenHandler.getDateTime(), Timestamp.valueOf(LocalDateTime.MIN).toLocalDateTime());
        tokenHandler.generateToken();
        assertNotEquals(tokenHandler.getDateTime(), Timestamp.valueOf(LocalDateTime.MIN).toLocalDateTime());
    }

    @Test
    void clearToken() {
        assertEquals(tokenHandler.getDateTime(), Timestamp.valueOf(LocalDateTime.MIN).toLocalDateTime());
    }

    @Test
    void getDateTime() {
        assertEquals(tokenHandler.getDateTime(), Timestamp.valueOf(LocalDateTime.MIN).toLocalDateTime());
        tokenHandler.generateToken();
        assertNotEquals(tokenHandler.getDateTime(), Timestamp.valueOf(LocalDateTime.MIN).toLocalDateTime());
    }
}