package nl.hva.ict.models;

import java.io.Serializable;

/**
 * Model voor Reiziger
 * @author HvA FDMCI HBO-ICT
 */
public class Reiziger implements Serializable {
    private String reizigerCode, voornaam, achternaam, adres, postcode, plaats, land;
    private Reiziger hoofdreiziger;

    public Reiziger(String voornaam, String achternaam, String plaats) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.plaats = plaats;
    }

    public Reiziger(String reizigerCode, String voornaam, String achternaam, String adres, String postcode,
                    String plaats, String land) {
        this.reizigerCode = reizigerCode;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adres = adres;
        this.postcode = postcode;
        this.plaats = plaats;
        this.land = land;
    }

    public Reiziger(String reizigerCode, String voornaam, String achternaam, String adres, String postcode,
                    String plaats, String land, Reiziger hoofdreiziger) {
        this.reizigerCode = reizigerCode;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adres = adres;
        this.postcode = postcode;
        this.plaats = plaats;
        this.land = land;
        this.hoofdreiziger = hoofdreiziger;
    }

    public String getReizigerCode() {
        return reizigerCode;
    }

    public void setReizigerCode(String reizigerCode) {
        this.reizigerCode = reizigerCode;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Reiziger getHoofdreiziger() {
        return hoofdreiziger;
    }

    public void setHoofdreiziger(Reiziger hoofdreiziger) {
        this.hoofdreiziger = hoofdreiziger;
    }

    @Override
    public String toString() {
        return String.format("%s %s woont in %s", voornaam, achternaam, plaats);
    }
}