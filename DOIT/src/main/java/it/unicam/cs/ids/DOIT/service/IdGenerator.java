package it.unicam.cs.ids.DOIT.service;

import java.util.Random;

public class IdGenerator implements IIdGenerator {
    private Random rnd;

    IdGenerator() {
        this.rnd = new Random();
    }

    public int getId() {
        return 100000 + rnd.nextInt(900000);
    }
}
