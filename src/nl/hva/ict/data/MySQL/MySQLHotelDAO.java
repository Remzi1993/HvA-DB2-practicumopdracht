package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.HotelDAO;
import nl.hva.ict.models.Hotel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLHotelDAO extends HotelDAO {
    private final MySQL mysql = new MySQL();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Hotel hotel) {
        return false;
    }

    @Override
    public List<Hotel> read() {
        try {
            // Alle hotels worden opgehaald - Remzi Cavdar
            String sql = """
                SELECT A.accommodatie_code, A.naam, A.stad, A.land, A.kamer, A.personen, H.prijs_per_nacht, H.ontbijt
                FROM hotel H
                INNER JOIN accommodatie A ON A.accommodatie_code = H.accommodatie_code;
                """;

            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            hotels.clear();

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

            return hotels;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Hotel hotel) {
        return false;
    }

    @Override
    public boolean delete(Hotel hotel) {
        // Als er geen object is wordt de methode afgebroken
        if (hotel == null) {
            System.err.println("Geen object meegegeven");
            return false;
        }

        /* HvA FDMCI Databases 2 practicumopdracht - week 4D
         * Roep in deze methode de stored function aan die eerder is aangemaakt in de database - Remzi Cavdar
         */
        String sql = "CALL verwijderAccommodatie(?);";

        try {
            // Maak je SQL-statement
            PreparedStatement ps = mysql.getStatement(sql);

            // Vervang het eerste vraagteken met de reizigerscode. Pas dit eventueel aan voor jou eigen query
            ps.setString(1, hotel.getAccommodatieCode());

            // Voer het uit
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}