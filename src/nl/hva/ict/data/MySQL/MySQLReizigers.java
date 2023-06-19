package nl.hva.ict.data.MySQL;

import nl.hva.ict.models.Reiziger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao voor reizigers
 * @author HvA FDMCI HBO-ICT
 */
public class MySQLReizigers extends MySQL<Reiziger> {
    private final List<Reiziger> reizigers;

    public MySQLReizigers() {
        reizigers = new ArrayList<>();

        // Laad bij startup
        load();
    }

    private void load() {
        /* HvA FDMCI Databases 2 practicumopdracht - week 2E
         * Alle reizigers worden opgehaald en getoond op de reizigers overzicht - Remzi Cavdar
         */
        String sql = "SELECT * FROM reiziger;";

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = executeSelectPreparedStatement(ps);

            System.out.println(rs);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model aan en voeg toe aan arraylist
                reizigers.add(new Reiziger(
                        rs.getString("reiziger_code"),
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getString("adres"),
                        rs.getString("postcode"),
                        rs.getString("plaats"),
                        rs.getString("land"),
                        rs.getString("hoofdreiziger")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Geef alle reizigers in de arraylist terug
     * @return arraylist met reizigers
     */
    @Override
    public List<Reiziger> getAll() {
        return reizigers;
    }

    /**
     * Haal 1 reiziger op
     * @return reiziger object
     */
    @Override
    public Reiziger get() {
        return null;
    }

    /**
     * Voeg reiziger toe
     * @param reiziger reiziger
     */
    @Override
    public void add(Reiziger reiziger) {

    }

    /**
     * Update reiziger
     * @param reiziger reiziger
     */
    @Override
    public void update(Reiziger reiziger) {

    }

    /**
     * Verwijder reiziger
     * @param object reiziger
     */
    @Override
    public void remove(Reiziger object) {

    }

    /**
     * Refresh het scherm
     */
    public void reload() {
        reizigers.clear();
        load();
    }
}