package nl.hva.ict.data;

import nl.hva.ict.models.Land;
import java.util.ArrayList;
import java.util.List;

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
}