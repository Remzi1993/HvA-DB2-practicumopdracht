package nl.hva.ict.data;

import nl.hva.ict.models.Hotel;
import java.util.ArrayList;
import java.util.List;

/**
 * HotelDAO is een abstracte class die de CRUD-principe toepast.
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public abstract class HotelDAO implements DAO<Hotel> {
    protected List<Hotel> hotels = new ArrayList<>();

    @Override
    public abstract boolean create(Hotel hotel);

    @Override
    public abstract List<Hotel> read();

    @Override
    public abstract boolean update(Hotel hotel);

    @Override
    public abstract boolean delete(Hotel hotel);
}