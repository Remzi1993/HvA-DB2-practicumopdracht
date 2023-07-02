package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.ReizigerDAO;
import nl.hva.ict.models.Reiziger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQLReizigerDAO is een class die de MySQL database connectie maakt en de CRUD-principe toepast.
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public class MySQLReizigerDAO extends ReizigerDAO {
    private final MySQL mysql = new MySQL();

    @Override
    public boolean create(Reiziger reiziger) {
        try {
            String sql = """
                    INSERT INTO reiziger (reiziger_code, voornaam, achternaam, adres, postcode, plaats, land, hoofdreiziger)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                    """;
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

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Reiziger> read() {
        try {
            /* HvA FDMCI Databases 2 practicumopdracht - week 2E
             * Alle reizigers worden opgehaald en getoond op de reizigers overzicht - Remzi Cavdar
             */
            String sql = "SELECT * FROM reiziger;";

            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            reizigers.clear();

            // Loop net zolang als er records zijn
            while (rs.next()) {
                reizigers.add(new Reiziger(
                        rs.getString("reiziger_code"),
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getString("adres"),
                        rs.getString("postcode"),
                        rs.getString("plaats"),
                        rs.getString("land"),
                        read(rs.getString("hoofdreiziger"))
                ));
            }

            // Geef arraylist met reizigers terug
            return reizigers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return een specifieke reiziger via de reizigerCode.
     */
    @Override
    public Reiziger read(String reizigerCode) {
        if(reizigerCode == null){
            return null;
        }

        try {
            String sql = "SELECT * FROM reiziger WHERE reiziger_code = ?;";

            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            ps.setString(1, reizigerCode);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            while (rs.next()) {
                if(rs.getString("hoofdreiziger") == null) {
                    return new Reiziger(
                            rs.getString("reiziger_code"),
                            rs.getString("voornaam"),
                            rs.getString("achternaam"),
                            rs.getString("adres"),
                            rs.getString("postcode"),
                            rs.getString("plaats"),
                            rs.getString("land"),
                            null
                    );
                } else {
                    // Haal de hoofdreiziger op
                    String sql2 = "SELECT * FROM reiziger WHERE reiziger_code = ?;";
                    PreparedStatement ps2 = mysql.getStatement(sql);
                    ps2.setString(1, rs.getString("hoofdreiziger"));
                    ResultSet rs2 = mysql.executeSelectPreparedStatement(ps);

                    Reiziger hoofdreiziger = new Reiziger(
                            rs2.getString("reiziger_code"),
                            rs2.getString("voornaam"),
                            rs2.getString("achternaam"),
                            rs2.getString("adres"),
                            rs2.getString("postcode"),
                            rs2.getString("plaats"),
                            rs2.getString("land"),
                            null
                    );

                    return new Reiziger(
                            rs.getString("reiziger_code"),
                            rs.getString("voornaam"),
                            rs.getString("achternaam"),
                            rs.getString("adres"),
                            rs.getString("postcode"),
                            rs.getString("plaats"),
                            rs.getString("land"),
                            hoofdreiziger
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Reiziger reiziger, String oudeReizigerCode) {
        List<String> reizigersCodes = new ArrayList<>();

        // Haal alle reizigers op die de oude reiziger als hoofdreiziger hebben en sla dit op in een arraylist
        try {
            String sql = "SELECT reiziger_code FROM reiziger where hoofdreiziger = ?;";

            PreparedStatement ps = mysql.getStatement(sql);

            ps.setString(1, oudeReizigerCode);

            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            reizigersCodes.clear();

            while (rs.next()) {
                reizigersCodes.add(
                        rs.getString("reiziger_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Update alle reizigers die de oude reiziger als hoofdreiziger hebben naar null (tijdelijk tussenstap)
        if (reizigersCodes.size() > 0) {
            try {
                String sql = """
                        UPDATE reiziger
                        SET hoofdreiziger = NULL
                        WHERE hoofdreiziger = ?;
                        """;
                PreparedStatement ps = mysql.getStatement(sql);

                ps.setString(1, oudeReizigerCode);

                // Voer het uit
                mysql.executeUpdatePreparedStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /* Update de (hoofd)reiziger - Dit is nu mogelijk zonder conflicten omdat er geen reizigers meer zijn die de
         * oude reiziger als hoofdreiziger hebben. (Dit is een tijdelijke tussenstap)
         */
        try {
            String sql = """
                    UPDATE reiziger
                    SET reiziger_code = ?, voornaam = ?, achternaam = ?, adres = ?, postcode = ?, plaats = ?, land = ?, hoofdreiziger = ?
                    WHERE reiziger_code = ?;
                    """;
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

            ps.setString(9, oudeReizigerCode);

            mysql.executeUpdatePreparedStatement(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        /* Update alle reizigers die de oude reiziger als hoofdreiziger hebben naar de nieuwe reizigerCode.
         * Update alleen als er reizigers zijn die de oude reiziger als hoofdreiziger hebben.
         */
        if (reizigersCodes.size() > 0) {
            try {
                String sql = """
                        UPDATE reiziger
                        SET hoofdreiziger = ?
                        WHERE reiziger_code = ?;
                        """;
                PreparedStatement ps = mysql.getStatement(sql);

                ps.setString(1, reiziger.getReizigerCode());

                for (String reizigerCode : reizigersCodes) {
                    ps.setString(2, reizigerCode);

                    // Voer het uit
                    mysql.executeUpdatePreparedStatement(ps);
                }

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }

        // Als er iets fout is gegaan return false
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String sql = "DELETE FROM reiziger WHERE reiziger_code = ?;";
            PreparedStatement ps = mysql.getStatement(sql);

            ps.setString(1, reiziger.getReizigerCode());

            mysql.executeUpdatePreparedStatement(ps);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}