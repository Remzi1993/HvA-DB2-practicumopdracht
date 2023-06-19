package nl.hva.ict.data.MySQL;

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

/**
 * DAO voor de accommodatie
 * @author HvA FDMCI HBO-ICT
 */
public class MySQLBoekingsoverzicht extends MySQL<Boekingsoverzicht> {
    private final List<Boekingsoverzicht> boekingsoverzicht;

    public MySQLBoekingsoverzicht() {
        boekingsoverzicht = new ArrayList<>();

        // Laad bij startup
        load();
    }

    /**
     * Doe dit bij het maken van dit object
     */
    private void load() {
        /* HvA FDMCI Databases 2 practicumopdracht - week 4B
         * Roep in deze methode de MySQL view aan die eerder is aangemaakt in de database en geef het resultaat terug
         * - Remzi Cavdar
         */
        String sql = "SELECT * FROM Boekingsoverzicht;";

        try {
            // Roep de methode aan in de parent class en geen je SQL door
            PreparedStatement ps = getStatement(sql);

            //Voer je query uit en stop het antwoord in een result set
            ResultSet rs = executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                String reizigerCode = rs.getString("reiziger_code");

                // Maak models aan
                Reservering reservering = new Reservering(
                        0,
                        rs.getDate("aankomst_datum"),
                        rs.getDate("vertrek_datum"),
                        rs.getBoolean("betaald"),
                        rs.getString("accommodatie_code"),
                        reizigerCode
                );

                Reiziger reiziger = new Reiziger(
                        reizigerCode,
                        "",
                        rs.getString("reiziger"),
                        "",
                        "",
                        "",
                        "",
                        ""
                );

                Accommodatie accommodatie = new Accommodatie(
                        rs.getString("naam"),
                        rs.getString("stad"),
                        rs.getString("land")
                );

                //voeg die toe aan de arraylist
                boekingsoverzicht.add(new Boekingsoverzicht(accommodatie, reiziger, reservering));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                SELECT R.reservering_id, R.aankomst_datum, R.vertrek_datum, R.betaald, R.accommodatie_code, RE.voornaam,
                RE.achternaam, RE.plaats, A.naam, A.stad, A.land
                FROM reservering R
                LEFT JOIN reiziger RE ON RE.reiziger_code = R.reiziger_code
                LEFT JOIN accommodatie A ON A.accommodatie_code = R.accommodatie_code
                WHERE R.reiziger_code = ?;
                """;

        try {
            // Maak je statement
            PreparedStatement ps = getStatement(sql);

            // Vervang het eerste vraagteken met de reizigerscode. Pas dit eventueel aan voor jou eigen query
            ps.setString(1, reizigerCode);

            // Voer het uit
            ResultSet rs = executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                // Maak model objecten
                Reservering reservering = new Reservering(
                        0,
                        rs.getDate("aankomst_datum"),
                        rs.getDate("vertrek_datum"),
                        rs.getBoolean("betaald"),
                        rs.getString("accommodatie_code"),
                        reizigerCode
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
                reserveringVoor.add(new Boekingsoverzicht(accommodatie, reiziger, reservering));
            }
        } catch (SQLException throwables) {
            // Oeps probleem
            throwables.printStackTrace();
        }

        // Geef de arrayList terug met de juiste reserveringen
        return reserveringVoor;
    }

    /**
     * Haal de reizigerscode op voor een bepaalde boeking per accommodate en datum
     *
     * @param pCode  de accommodatie code
     * @param pDatum de datum van verblijf
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

        // convert datum naar ander formaat
        Date date = Date.valueOf(pDatum);

        try {
            // query voorbereiden
            PreparedStatement ps = getStatement(sql);

            // Vervang de vraagtekens met de juiste waarde. Pas eventueel aan je eigen query
            ps.setString(1, pCode);
            ps.setDate(2, date);

            // Voer het uit
            ResultSet rs = executeSelectPreparedStatement(ps);

            // Loop net zolang als er records zijn
            while (rs.next()) {
                reizigerCode = rs.getString("reizigerCode");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Geef de reizigercode terug
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
                PreparedStatement ps = getStatement(sql);

                // vervang de eerste vraagteken met de reizigerscode
                ps.setString(1, reizigerscode);

                // Voer je query uit
                ResultSet rs = executeSelectPreparedStatement(ps);

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
                            rs.getString("hoofdreiziger")
                    ));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // Geef de array terug met reserveringen
        return geboektOp;
    }

    /**
     * Haal alle boekingen op door de gehele arraylist terug te geven
     * @return Een arraylist van accommodaties
     */
    @Override
    public List<Boekingsoverzicht> getAll() {
        return boekingsoverzicht;
    }

    /**
     * Haal 1 boeking op
     * @return Een arraylist van accommodaties
     */
    @Override
    public Boekingsoverzicht get() {
        // nog niet uitgewerkt geeft nu even null terug
        return null;
    }

    /**
     * Voeg een boeking toe
     * @param boekingsoverzicht de boeking
     */
    @Override
    public void add(Boekingsoverzicht boekingsoverzicht) {
        // nog niet uitgewerkt
    }

    /**
     * Update de boeking
     * @param boekingsoverzicht de boeking
     */
    @Override
    public void update(Boekingsoverzicht boekingsoverzicht) {
        // nog niet uitgewerkt

    }

    /**
     * Verwijder een boeking
     * @param object de boeking
     */
    @Override
    public void remove(Boekingsoverzicht object) {
        // nog niet uitgewerkt

    }
}