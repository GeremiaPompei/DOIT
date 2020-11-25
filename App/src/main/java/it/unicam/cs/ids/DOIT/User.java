package it.unicam.cs.ids.DOIT;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String surname;
    private List<String> generalities;
    private GestoreRuoli gestoreRuoli;

    public User(int id, String name, String surname, List<String> generalities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.generalities = generalities;
        this.gestoreRuoli = new GestoreRuoli();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getGeneralities() {
        return generalities;
    }

    public GestoreRuoli getGestoreRuoli() {
        return gestoreRuoli;
    }
}
