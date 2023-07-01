package nl.hva.ict.data;

import nl.hva.ict.models.Accommodatie;
import java.util.ArrayList;
import java.util.List;

public abstract class AccommodatieDAO implements DAO<Accommodatie> {
    protected List<Accommodatie> accommodaties = new ArrayList<>();

    /* Deze DAO is alleen bedoeld om all accommodaties op te halen (read) uit de database.
     * Dit wordt gebruikt bij BoekingsGeboektOpController
     * Daarom geven we de andere methodes een return van false terug en worden ze in de child classes niet geïmplementeerd.
     */

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