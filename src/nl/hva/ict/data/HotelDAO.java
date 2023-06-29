package nl.hva.ict.data;

import nl.hva.ict.models.Hotel;
import java.util.ArrayList;
import java.util.List;

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