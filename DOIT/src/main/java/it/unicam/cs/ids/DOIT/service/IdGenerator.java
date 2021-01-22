package it.unicam.cs.ids.DOIT.service;

import java.util.Random;

public class IdGenerator {

    public static Long getId() {
        return (long)(100000 + new Random().nextInt(900000));
    }
}
