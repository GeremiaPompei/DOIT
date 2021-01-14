package it.unicam.cs.ids.DOIT.role;

public class RoleException extends Exception {

    @Override
    public String getMessage() {
        return "Ruolo non disponibile";
    }
}
