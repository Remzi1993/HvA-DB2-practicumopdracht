package nl.hva.ict.data.MySQL;

import nl.hva.ict.models.Lodge;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao voor lodges
 * @author HvA FDMCI HBO-ICT
 */
public class MySQLLodge extends MySQL<Lodge> {
    private final List<Lodge> lodges;

    public MySQLLodge() {
        lodges = new ArrayList<>();

        // Laad bij startup
        load();
    }

    private void load() {
        // Alle lodges worden opgehaald - Remzi Cavdar
        String sql = """
                SELECT A.accommodatie_code, A.naam, A.stad, A.land, A.kamer, A.personen, L.prijs_per_week, L.autohuur
                FROM lodge L
                INNER JOIN accommodatie A ON A.accommodatie_code = L.accommodatie_code;
                """;

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model aan en voeg toe aan arraylist
                lodges.add(new Lodge(
                        rs.getString("accommodatie_code"),
                        rs.getString("naam"),
                        rs.getString("stad"),
                        rs.getString("land"),
                        rs.getString("kamer"),
                        rs.getInt("personen"),
                        rs.getDouble("prijs_per_week"),
                        rs.getBoolean("autohuur")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Geef alle lodges in de arraylist terug
     * @return arraylist met lodges
     */
    @Override
    public List<Lodge> getAll() {
        return lodges;
    }

    /**
     * Haal 1 lodge op
     * @return lodge object
     */
    @Override
    public Lodge get() {
        return null;
    }

    /**
     * Voeg een lodge toe
     * @param lodge Lodge
     */
    @Override
    public void add(Lodge lodge) {

    }

    /**
     * Update een lodge
     * @param lodge Lodge
     */
    @Override
    public void update(Lodge lodge) {

    }

    /**
     * Verwijder een lodge
     * @param object Lodge
     */
    @Override
    public void remove(Lodge object) {

    }

    /**
     * Refresh het scherm
     */
    public void reload() {
        lodges.clear();
        load();
    }
}