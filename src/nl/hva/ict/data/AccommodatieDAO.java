package nl.hva.ict.data;

import nl.hva.ict.models.Accommodatie;
import java.util.ArrayList;
import java.util.List;

public abstract class AccommodatieDAO implements DAO<Accommodatie> {
    protected List<Accommodatie> accommodaties = new ArrayList<>();

    @Override
    public abstract boolean create(Accommodatie accommodatie);

    @Override
    public abstract List<Accommodatie> read();

    @Override
    public abstract boolean update(Accommodatie accommodatie);

    @Override
    public abstract boolean delete(Accommodatie accommodatie);
}