package nl.hva.ict.models;

import java.io.Serializable;

/**
 * Model voor Lodge
 * @author HvA FDMCI HBO-ICT
 */
public class Lodge extends Accommodatie implements Serializable {
    private double prijsPerWeek;
    private boolean autoHuren;

    public Lodge(String accommodatieCode, String naam, String stad, String land, String kamer, int personen,
                 double prijsPerWeek, boolean autoHuren) {
        super(accommodatieCode, naam, stad, land, kamer, personen);
        this.prijsPerWeek = prijsPerWeek;
        this.autoHuren = autoHuren;
    }

    public double getPrijsPerWeek() {
        return prijsPerWeek;
    }

    public void setPrijsPerWeek(double prijsPerWeek) {
        this.prijsPerWeek = prijsPerWeek;
    }

    public boolean isAutoHuren() {
        return autoHuren;
    }

    public void setAutoHuren(boolean autoHuren) {
        this.autoHuren = autoHuren;
    }
}