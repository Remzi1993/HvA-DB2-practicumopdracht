package nl.hva.ict.models;

import nl.hva.ict.data.Identifable;

/**
 * Model voor accommodatie
 * @author HvA FDMCI HBO-ICT
 */
public class Accommodatie implements Identifable {
    private String accommodatieCode, naam, stad, land, kamer;
    private int personen;

    public Accommodatie() {
    }

    public Accommodatie(String naam, String stad, String land) {
        this.naam = naam;
        this.stad = stad;
        this.land = land;
    }

    public Accommodatie(String accommodatieCode, String naam, String stad, String land, String kamer, int personen) {
        this.accommodatieCode = accommodatieCode;
        this.naam = naam;
        this.stad = stad;
        this.land = land;
        this.kamer = kamer;
        this.personen = personen;
    }

    public String getAccommodatieCode() {
        return accommodatieCode;
    }

    public void setAccommodatieCode(String accommodatieCode) {
        this.accommodatieCode = accommodatieCode;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getKamer() {
        return kamer;
    }

    public void setKamer(String kamer) {
        this.kamer = kamer;
    }

    public int getPersonen() {
        return personen;
    }

    public void setPersonen(int personen) {
        this.personen = personen;
    }

    @Override
    public String toString() {
        return naam + " - " + stad + " - " + land;
    }
}