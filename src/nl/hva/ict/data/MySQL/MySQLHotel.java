package nl.hva.ict.data.MySQL;

import nl.hva.ict.models.Hotel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao voor hotels
 * @author HvA FDMCI HBO-ICT
 */
public class MySQLHotel extends MySQL<Hotel> {
    private final List<Hotel> hotels;

    public MySQLHotel() {
        hotels = new ArrayList<>();

        // Laad bij startup
        load();
    }

    private void load() {
        // Alle hotels worden opgehaald - Remzi Cavdar
        String sql = """
                SELECT A.accommodatie_code, A.naam, A.stad, A.land, A.kamer, A.personen, H.prijs_per_nacht, H.ontbijt
                FROM hotel H
                INNER JOIN accommodatie A ON A.accommodatie_code = H.accommodatie_code;
                """;

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = getStatement(sql);

            //Voer je query uit en stop het antwoord in een result set
            ResultSet rs = executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model aan en voeg toe aan arraylist
                hotels.add(new Hotel(
                        rs.getString("accommodatie_code"),
                        rs.getString("naam"),
                        rs.getString("stad"),
                        rs.getString("land"),
                        rs.getString("kamer"),
                        rs.getInt("personen"),
                        rs.getDouble("prijs_per_nacht"),
                        rs.getBoolean("ontbijt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Geef alle hotels in de arraylist terug
     * @return arraylist met hotels
     */
    @Override
    public List<Hotel> getAll() {
        return hotels;
    }

    /**
     * Haal 1 hotel op
     * @return hotel object
     */
    @Override
    public Hotel get() {
        return null;
    }

    /**
     * Voeg een hotel toe
     * @param hotel hotel
     */
    @Override
    public void add(Hotel hotel) {

    }

    /**
     * Update een hotel
     * @param hotel hotel
     */
    @Override
    public void update(Hotel hotel) {

    }

    /**
     * Verwijder een hotel
     * @param object het hotel
     */
    @Override
    public void remove(Hotel object) {
        /* HvA FDMCI Databases 2 practicumopdracht - week 4D
         * Roep in deze methode de stored function aan die eerder is aangemaakt in de database - Remzi Cavdar
         */
        String sql = "CALL verwijderAccommodatie(?);";

        // Als er geen object is wordt de methode afgebroken
        if (object == null)
            return;

        try {
            // Maak je statement
            PreparedStatement ps = getStatement(sql);

            // Vervang het eerste vraagteken met de reizigerscode. Pas dit eventueel aan voor jou eigen query
            ps.setString(1, object.getAccommodatieCode());

            // Voer het uit
            ResultSet rs = executeSelectPreparedStatement(ps);
            reload();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh het scherm
     */
    public void reload() {
        // Leeg arraylist
        hotels.clear();

        // Laad de data weer opnieuw
        load();
    }
}