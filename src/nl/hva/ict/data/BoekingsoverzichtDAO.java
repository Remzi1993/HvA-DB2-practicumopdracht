package nl.hva.ict.data;

import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class BoekingsoverzichtDAO implements DAO<Boekingsoverzicht> {
    protected List<Boekingsoverzicht> boekingsoverzicht = new ArrayList<>();

    @Override
    public abstract boolean create(Boekingsoverzicht boekingsoverzicht);

    @Override
    public abstract List<Boekingsoverzicht> read();

    @Override
    public abstract boolean update(Boekingsoverzicht boekingsoverzicht);

    @Override
    public abstract boolean delete(Boekingsoverzicht boekingsoverzicht);

    public abstract List<Boekingsoverzicht> getBoekingVoor(String reizigerCode);

    public abstract List<Reiziger> GeboektOp(String pCode, LocalDate pDatum);
}