package nl.hva.ict.data;

import nl.hva.ict.models.Reiziger;
import java.util.ArrayList;
import java.util.List;

/**
 * ReinigerDAO is een abstracte class die de CRUD-principe toepast.
 */
public abstract class ReizigerDAO implements DAO<Reiziger> {
    protected List<Reiziger> reizigers = new ArrayList<>();

    @Override
    public abstract boolean create(Reiziger reiziger);

    @Override
    public abstract List<Reiziger> read();

    public abstract Reiziger read(String reizigerCode);

    @Override
    public boolean update(Reiziger reiziger) {
        return update(reiziger, reiziger.getReizigerCode());
    }

    public abstract boolean update(Reiziger reiziger, String oudeReizigerCode);

    @Override
    public abstract boolean delete(Reiziger reiziger);
}