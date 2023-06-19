package nl.hva.ict.models;

/**
 * Model voor Landen
 * @author HvA FDMCI HBO-ICT
 */
public class Landen {
    private final String naam, hoofdstad;

    public Landen(String naam, String hoofdstad) {
        this.naam = naam;
        this.hoofdstad = hoofdstad;
    }

    @Override
    public String toString() {
        return naam + ". - De hoofdstad van " + naam + " is " + hoofdstad;
    }
}