package it.unicam.cs.ids.DOIT.model;

public class RoleException extends Exception {

    @Override
    public String getMessage() {
        return "Ruolo non disponibile";
    }
}
