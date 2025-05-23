package nl.hva.ict.models;

import java.io.Serializable;

/**
 * Model voor Hotel
 * @author HvA FDMCI HBO-ICT
 */
public class Hotel extends Accommodatie implements Serializable {
    private double prijsPerNacht;
    private boolean ontbijt;

    public Hotel(String accommodatieCode, String naam, String stad, String land, String kamer, int personen,
                 double prijsPerNacht, boolean ontbijt) {
        super(accommodatieCode, naam, stad, land, kamer, personen);
        this.prijsPerNacht = prijsPerNacht;
        this.ontbijt = ontbijt;
    }

    public double getPrijsPerNacht() {
        return prijsPerNacht;
    }

    public void setPrijsPerNacht(double prijsPerNacht) {
        this.prijsPerNacht = prijsPerNacht;
    }

    public boolean isOntbijt() {
        return ontbijt;
    }

    public void setOntbijt(boolean ontbijt) {
        this.ontbijt = ontbijt;
    }
}