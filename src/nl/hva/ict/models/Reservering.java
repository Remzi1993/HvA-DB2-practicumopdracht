package nl.hva.ict.models;

import java.io.Serializable;
import java.sql.Date;

/**
 * Model voor Reservering
 *
 * @author HvA FDMCI HBO-ICT
 */
public class Reservering implements Serializable {
    private int reserveringId;
    private Date aankomstDatum, vertrekDatum;
    private boolean betaald;
    private String accommodatieCode;
    private Reiziger reiziger;

    public Reservering(int reserveringId, Date aankomstDatum, Date vertrekDatum, boolean betaald,
                       String accommodatieCode, Reiziger reiziger) {
        this.reserveringId = reserveringId;
        this.aankomstDatum = aankomstDatum;
        this.vertrekDatum = vertrekDatum;
        this.betaald = betaald;
        this.accommodatieCode = accommodatieCode;
        this.reiziger = reiziger;
    }

    public int getReserveringId() {
        return reserveringId;
    }

    public void setReserveringId(int reserveringId) {
        this.reserveringId = reserveringId;
    }

    public Date getAankomstDatum() {
        return aankomstDatum;
    }

    public void setAankomstDatum(Date aankomstDatum) {
        this.aankomstDatum = aankomstDatum;
    }

    public Date getVertrekDatum() {
        return vertrekDatum;
    }

    public void setVertrekDatum(Date vertrekDatum) {
        this.vertrekDatum = vertrekDatum;
    }

    public boolean isBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }

    public String getAccommodatieCode() {
        return accommodatieCode;
    }

    public void setAccommodatieCode(String accommodatieCode) {
        this.accommodatieCode = accommodatieCode;
    }

    @Override
    public String toString() {
        return String.format("""
                    Reservering { idReservering = %d, aankomstDatum = %s, vertrekDatum = %s, betaald = %b,\s
                    accommodatieCode = %s, reizigerCode = %s }
                """, reserveringId, aankomstDatum, vertrekDatum, betaald, accommodatieCode, reiziger.getReizigerCode()
        );
    }
}