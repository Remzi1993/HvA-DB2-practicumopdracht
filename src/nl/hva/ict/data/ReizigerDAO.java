package nl.hva.ict.data;

import nl.hva.ict.models.Reiziger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ReinigerDAO is een abstracte class die de CRUD-principe toepast.
 */
public abstract class ReizigerDAO implements DAO<Reiziger> {
    protected List<Reiziger> reizigers = new ArrayList<>();

    @Override
    public abstract boolean create(Reiziger reiziger);

    @Override
    public abstract List<Reiziger> read();

    /**
     * @return een specifieke reiziger via de reizigerCode.
     */
    public Reiziger read(String reizigerCode) {
        if(reizigerCode == null){
            return null;
        }

        try {
            for (Reiziger reiziger : reizigers) {
                if (Objects.equals(reiziger.getReizigerCode(), reizigerCode)) {
                    return reiziger;
                }
            }

            return null;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Exceeded list size");
            return null;
        } catch (Exception e) {
            System.err.println("Something went wrong!");
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Reiziger reiziger) {
        return update(reiziger, reiziger.getReizigerCode());
    }

    public abstract boolean update(Reiziger reiziger, String oudeReizigerCode);

    @Override
    public abstract boolean delete(Reiziger reiziger);
}