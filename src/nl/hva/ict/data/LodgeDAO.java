package nl.hva.ict.data;

import nl.hva.ict.models.Lodge;
import java.util.ArrayList;
import java.util.List;

public abstract class LodgeDAO implements DAO<Lodge> {
    protected List<Lodge> lodges = new ArrayList<>();

    @Override
    public abstract boolean create(Lodge lodge);

    @Override
    public abstract List<Lodge> read();

    @Override
    public abstract boolean update(Lodge lodge);

    @Override
    public abstract boolean delete(Lodge lodge);
}