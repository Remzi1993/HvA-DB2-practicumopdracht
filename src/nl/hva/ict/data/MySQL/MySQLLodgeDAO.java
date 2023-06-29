package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.LodgeDAO;
import nl.hva.ict.models.Lodge;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLLodgeDAO extends LodgeDAO {
    private final MySQL mysql = new MySQL();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Lodge lodge) {
        return false;
    }

    @Override
    public List<Lodge> read() {
        // Alle lodges worden opgehaald - Remzi Cavdar
        String sql = """
                SELECT A.accommodatie_code, A.naam, A.stad, A.land, A.kamer, A.personen, L.prijs_per_week, L.autohuur
                FROM lodge L
                INNER JOIN accommodatie A ON A.accommodatie_code = L.accommodatie_code;
                """;

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            lodges.clear();

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

            return lodges;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Lodge lodge) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Lodge lodge) {
        return false;
    }
}