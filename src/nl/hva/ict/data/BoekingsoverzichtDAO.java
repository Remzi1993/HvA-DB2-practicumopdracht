package nl.hva.ict.data;

import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BoekingsoverzichtDAO is een abstracte class.
 * Omdat het over een overzicht gaat is het niet nodig om een boekingsoverzicht te kunnen aanmaken (create),
 * aanpassen (update) of verwijderen (delete).
 * Daarom geven deze methodes een return van false terug en worden ze in de child classes niet geïmplementeerd.
 *
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public abstract class BoekingsoverzichtDAO implements DAO<Boekingsoverzicht> {
    protected List<Boekingsoverzicht> boekingsoverzicht = new ArrayList<>();

    @Override
    public boolean create(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    @Override
    public abstract List<Boekingsoverzicht> read();

    @Override
    public boolean update(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    @Override
    public boolean delete(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    public abstract List<Boekingsoverzicht> getBoekingVoor(String reizigerCode);

    public abstract List<Reiziger> GeboektOp(String pCode, LocalDate pDatum);
}