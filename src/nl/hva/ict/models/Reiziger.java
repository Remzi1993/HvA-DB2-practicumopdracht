package nl.hva.ict.models;

import nl.hva.ict.data.Identifable;

import java.io.Serializable;

/**
 * Model voor Reiziger
 *
 * @author HvA FDMCI HBO-ICT
 */
public class Reiziger implements Identifable, Serializable {
    private String reizigersCode, voornaam, achternaam, adres, postcode, plaats, land;
    private Reiziger hoofdreiziger;

    public Reiziger() {
    }

    public Reiziger(String voornaam, String achternaam, String plaats) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.plaats = plaats;
    }

    public Reiziger(String reizigersCode, String voornaam, String achternaam, String adres, String postcode,
                    String plaats, String land, Reiziger hoofdreiziger) {
        this.reizigersCode = reizigersCode;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adres = adres;
        this.postcode = postcode;
        this.plaats = plaats;
        this.land = land;
        this.hoofdreiziger = hoofdreiziger;
    }

    public String getReizigersCode() {
        return reizigersCode;
    }

    public void setReizigersCode(String reizigersCode) {
        this.reizigersCode = reizigersCode;
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
        return voornaam + " " + achternaam + " woont in " + plaats;
    }
}