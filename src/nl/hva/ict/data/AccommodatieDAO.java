package nl.hva.ict.data;

import nl.hva.ict.models.Accommodatie;
import java.util.ArrayList;
import java.util.List;

/**
 * AccommodatieDAO is een abstracte class.
 * Deze DAO is alleen bedoeld om alle accommodaties op te halen (read) uit de database.
 * Dit wordt gebruikt bij BoekingsGeboektOpController
 * Daarom geven we de andere methodes een return van false terug en worden ze in de child classes niet geïmplementeerd.
 *
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public abstract class AccommodatieDAO implements DAO<Accommodatie> {
    protected List<Accommodatie> accommodaties = new ArrayList<>();

    @Override
    public boolean create(Accommodatie accommodatie) {
        return false;
    }

    @Override
    public abstract List<Accommodatie> read();

    @Override
    public boolean update(Accommodatie accommodatie) {
        return false;
    }

    @Override
    public boolean delete(Accommodatie accommodatie) {
        return false;
    }
}