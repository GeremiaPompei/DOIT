package it.unicam.cs.ids.DOIT.service;

import java.util.Random;

public class IdGenerator {
    private Random rnd = new Random();

    public Long getId() {
        return (long)(100000 + rnd.nextInt(900000));
    }
}
