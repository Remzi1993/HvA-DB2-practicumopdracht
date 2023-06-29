package nl.hva.ict.models;

/**
 * Model voor BoekingsOverzicht
 *
 * @author HvA FDMCI HBO-ICT
 */
public class Boekingsoverzicht {
    private Accommodatie accommodatie;
    private Reiziger reiziger;
    private Reservering reservering;

    public Boekingsoverzicht(Reservering reservering, Accommodatie accommodatie, Reiziger reiziger) {
        this.reservering = reservering;
        this.accommodatie = accommodatie;
        this.reiziger = reiziger;

    }

    public Accommodatie getAccommodatie() {
        return accommodatie;
    }

    public void setAccommodatie(Accommodatie accommodatie) {
        this.accommodatie = accommodatie;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public Reservering getReservering() {
        return reservering;
    }

    public void setReservering(Reservering reservering) {
        this.reservering = reservering;
    }

    @Override
    public String toString() {
        return String.format("%s %s - reist van %s tot %s en verblijft in: %s in %s - %s", reiziger.getVoornaam(),
                reiziger.getAchternaam(), reservering.getAankomstDatum(), reservering.getVertrekDatum(),
                accommodatie.getNaam(), accommodatie.getStad(), accommodatie.getLand()
        );
    }
}