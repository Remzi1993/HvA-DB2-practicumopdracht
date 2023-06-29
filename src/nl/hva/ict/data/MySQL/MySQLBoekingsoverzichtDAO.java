package nl.hva.ict.data.MySQL;

import nl.hva.ict.data.BoekingsoverzichtDAO;
import nl.hva.ict.models.Accommodatie;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.models.Reservering;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static nl.hva.ict.MainApplication.getReizigerDAO;

public class MySQLBoekingsoverzichtDAO extends BoekingsoverzichtDAO {
    private final MySQL mysql = new MySQL();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    @Override
    public List<Boekingsoverzicht> read() {
        /* HvA FDMCI Databases 2 practicumopdracht - week 4B
         * Roep in deze methode de MySQL view aan die eerder is aangemaakt in de database en geef het resultaat terug
         * - Remzi Cavdar
         */
        String sql = "SELECT * FROM Boekingsoverzicht;";

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = mysql.getStatement(sql);

            // Voer je query uit en stop het antwoord in een result set
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Maak arraylist leeg
            boekingsoverzicht.clear();

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak models aan
                Reservering reservering = new Reservering(
                        0,
                        rs.getDate("aankomst_datum"),
                        rs.getDate("vertrek_datum"),
                        rs.getBoolean("betaald"),
                        rs.getString("accommodatie_code"),
                        getReizigerDAO().read(rs.getString("reiziger_code"))
                );

                Accommodatie accommodatie = new Accommodatie(
                        rs.getString("naam"),
                        rs.getString("stad"),
                        rs.getString("land")
                );

                Reiziger reiziger = getReizigerDAO().read(rs.getString("reiziger_code"));

                // Voeg die toe aan de arraylist
                boekingsoverzicht.add(new Boekingsoverzicht(reservering, accommodatie, reiziger));
            }

            return boekingsoverzicht;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Boekingsoverzicht boekingsoverzicht) {
        return false;
    }

    /**
     * Haal de boekingen op voor 1 reiziger
     *
     * @param reizigerCode Welke reiziger wil je de boekingen voor?
     * @return Een list van boekingen
     */
    public List<Boekingsoverzicht> getBoekingVoor(String reizigerCode) {
        List<Boekingsoverzicht> reserveringVoor = new ArrayList<>();

        /* HvA FDMCI Databases 2 practicumopdracht - week 3B
         * Voert een query uit die alle reserveringen, gecombineerd met de juiste accommodatie en reizigersgegevens
         * uit de database haalt voor de klant met een specifieke reizigerscode - Remzi Cavdar
         */
        String sql = """
                SELECT RS.*, AC.naam, AC.stad, AC.land, RE.voornaam, RE.achternaam, RE.plaats
                FROM reservering RS
                INNER JOIN accommodatie AC ON AC.accommodatie_code = RS.accommodatie_code
                INNER JOIN reiziger RE ON RE.reiziger_code = RS.reiziger_code
                WHERE RS.reiziger_code = ?;
                """;

        try {
            // Maak je statement
            PreparedStatement ps = mysql.getStatement(sql);

            // Vervang het eerste vraagteken met de reizigerscode. Pas dit eventueel aan voor jou eigen query
            ps.setString(1, reizigerCode);

            // Voer het uit
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model objecten en haal de data uit de database
                Reservering reservering = new Reservering(
                        0,
                        rs.getDate("aankomst_datum"),
                        rs.getDate("vertrek_datum"),
                        rs.getBoolean("betaald"),
                        rs.getString("accommodatie_code"),
                        getReizigerDAO().read(rs.getString("reiziger_code"))
                );

                Accommodatie accommodatie = new Accommodatie(
                        rs.getString("naam"),
                        rs.getString("stad"),
                        rs.getString("land")
                );

                Reiziger reiziger = new Reiziger(
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getString("plaats")
                );

                // Voeg de reservering toe aan de arraylist
                reserveringVoor.add(new Boekingsoverzicht(reservering, accommodatie, reiziger));
            }

            // Geef de arrayList terug met de juiste reserveringen
            return reserveringVoor;
        } catch (SQLException throwables) {
            // Oeps probleem
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Haal de reizigerscode op voor een bepaalde boeking per accommodate en datum
     *
     * @param pCode  De accommodatie code
     * @param pDatum De datum van verblijf
     * @return De reizigerscode
     */
    private String getReizigerscode(String pCode, LocalDate pDatum) {
        /* HvA FDMCI Databases 2 practicumopdracht - week 3F
         * Roep in deze methode de stored function aan die is aangemaakt in de database en geef het resultaat terug
         * - Remzi Cavdar
         */
        String sql = "SELECT GeboektOp(?, ?) AS reizigerCode";

        // default waarde
        String reizigerCode = "";

        // Convert datum naar ander formaat
        Date date = Date.valueOf(pDatum);

        try {
            // query voorbereiden
            PreparedStatement ps = mysql.getStatement(sql);

            // Vervang de vraagtekens met de juiste waarde. Pas eventueel aan je eigen query
            ps.setString(1, pCode);
            ps.setDate(2, date);

            // Voer het uit
            ResultSet rs = mysql.executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Haal de reizigerscode op
                reizigerCode = rs.getString("reizigerCode");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Geef de reizigerscode terug
        return reizigerCode;
    }

    /**
     * Haal een lijst met reserveringen op voor een bepaalde boeking per accommodate en datum
     *
     * @param pCode  de accommodate code
     * @param pDatum de datum van verblijf
     * @return Lijst met reserveringen
     */
    public List<Reiziger> GeboektOp(String pCode, LocalDate pDatum) {
        // Init arraylist
        List<Reiziger> geboektOp = new ArrayList<>();

        //Stop null pointer error als datum nog niet is ingevuld.
        if (pDatum == null)
            return geboektOp;

        // Spring naar de methode hierboven om de reizigerscode op te halen voor deze boeking
        String reizigerscode = getReizigerscode(pCode, pDatum);

        if (reizigerscode != null) {
            // Haal alle reserveringen op
            String sql = "SELECT * FROM reiziger R WHERE R.reiziger_code = ?;";

            try {
                // Roep de methode aan in de parent class en geen je SQL door
                PreparedStatement ps = mysql.getStatement(sql);

                // vervang de eerste vraagteken met de reizigerscode
                ps.setString(1, reizigerscode);

                // Voer je query uit
                ResultSet rs = mysql.executeSelectPreparedStatement(ps);

                // Loop net zolang als er records zijn
                while (rs.next()) {
                    // Maak reserveringsobject en voeg direct toe aan arraylist
                    geboektOp.add(new Reiziger(
                            reizigerscode,
                            rs.getString("voornaam"),
                            rs.getString("achternaam"),
                            rs.getString("adres"),
                            rs.getString("postcode"),
                            rs.getString("plaats"),
                            rs.getString("land"),
                            (Reiziger) rs.getObject("hoofdreiziger")
                    ));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // Geef de array terug met reserveringen
        return geboektOp;
    }
}