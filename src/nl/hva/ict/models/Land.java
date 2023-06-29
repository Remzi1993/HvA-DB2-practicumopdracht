package nl.hva.ict.models;

/**
 * Model voor Landen
 * @author HvA FDMCI HBO-ICT
 */
public class Land {
    private final String naam, hoofdstad;

    public Land(String naam, String hoofdstad) {
        this.naam = naam;
        this.hoofdstad = hoofdstad;
    }

    @Override
    public String toString() {
        return String.format("%s. - De hoofdstad van %s is %s", naam, naam, hoofdstad);
    }
}