package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.ReizigerDAO;
import nl.hva.ict.models.Reiziger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static nl.hva.ict.MainApplication.getReizigerDAO;

public class MySQLReizigerDAO extends ReizigerDAO {
    private final MySQL mysql = new MySQL();

    @Override
    public boolean create(Reiziger reiziger) {
        String sql = """
                INSERT INTO reiziger (reiziger_code, voornaam, achternaam, adres, postcode, plaats, land, hoofdreiziger)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try {
            PreparedStatement ps = mysql.getStatement(sql);

            ps.setString(1, reiziger.getReizigerCode());
            ps.setString(2, reiziger.getVoornaam());
            ps.setString(3, reiziger.getAchternaam());
            ps.setString(4, reiziger.getAdres());
            ps.setString(5, reiziger.getPostcode());
            ps.setString(6, reiziger.getPlaats());
            ps.setString(7, reiziger.getLand());

            if (reiziger.getHoofdreiziger() == null) {
                ps.setString(8, null);
            } else {
                ps.setString(8, reiziger.getHoofdreiziger().getReizigerCode());
            }

            // Voer het uit
            mysql.executeUpdatePreparedStatement(ps);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Reiziger> read() {
        /* HvA FDMCI Databases 2 practicumopdracht - week 2E
         * Alle reizigers worden opgehaald en getoond op de reizigers overzicht - Remzi Cavdar
         */
        String sql = "SELECT * FROM reiziger;";

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            reizigers.clear();

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model aan en voeg toe aan arraylist
                if (rs.getObject("hoofdreiziger") == null || rs.getString("hoofdreiziger").equals("")) {
                    reizigers.add(new Reiziger(
                            rs.getString("reiziger_code"),
                            rs.getString("voornaam"),
                            rs.getString("achternaam"),
                            rs.getString("adres"),
                            rs.getString("postcode"),
                            rs.getString("plaats"),
                            rs.getString("land")
                    ));
                } else {
                    reizigers.add(new Reiziger(
                            rs.getString("reiziger_code"),
                            rs.getString("voornaam"),
                            rs.getString("achternaam"),
                            rs.getString("adres"),
                            rs.getString("postcode"),
                            rs.getString("plaats"),
                            rs.getString("land"),
                            getReizigerDAO().read(rs.getString("hoofdreiziger"))
                    ));
                }
            }

            // Geef arraylist met reizigers terug
            return reizigers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }
}