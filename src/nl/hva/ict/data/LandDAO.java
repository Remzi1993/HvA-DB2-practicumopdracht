package nl.hva.ict.data;

import nl.hva.ict.models.Land;
import java.util.ArrayList;
import java.util.List;

/**
 * LandDAO is een abstracte class die de CRUD-principe toepast.
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public abstract class LandDAO implements DAO<Land> {
    protected List<Land> landen = new ArrayList<>();

    @Override
    public abstract boolean create(Land land);

    @Override
    public abstract List<Land> read();

    @Override
    public abstract boolean update(Land land);

    @Override
    public abstract boolean delete(Land land);

    public abstract void wieSpreekt(String taal, boolean alleenAfrika);

    public abstract void waarBetaalJeMet(String valuta, boolean alleenAfrika);

    public abstract void welkeLandenZijnErIn(String werelddeel);

    public abstract int hoeveelInwonersOostAfrika();
}