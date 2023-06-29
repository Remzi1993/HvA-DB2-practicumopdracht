package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.AccommodatieDAO;
import nl.hva.ict.models.Accommodatie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLAccommodatieDAO extends AccommodatieDAO {
    private final MySQL mysql = new MySQL();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Accommodatie accommodatie) {
        return false;
    }

    @Override
    public List<Accommodatie> read() {
        // Vul hier je SQL code in
        String sql = "SELECT * FROM accommodatie;";

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            //Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            accommodaties.clear();

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Haal alle velden op. Heb je een database ontwerp met andere velden? Pas dan hier de veldnamen aan
                String accommodatieCode = rs.getString("accommodatie_code");
                String naam = rs.getString("naam");
                String stad = rs.getString("stad");
                String land = rs.getString("land");
                String kamer = rs.getString("kamer");
                int personen = rs.getInt("personen");
                // Maak een object en voeg die toe aan de arraylist
                accommodaties.add(new Accommodatie(accommodatieCode, naam, stad, land, kamer, personen));
            }

            return accommodaties;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Accommodatie accommodatie) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Accommodatie accommodatie) {
        return false;
    }
}