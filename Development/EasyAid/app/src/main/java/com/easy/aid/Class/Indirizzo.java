package com.easy.aid.Class;

public class Indirizzo {

    private String provincia;
    private String citta;
    private String via;
    private String CAP;
    private int numeroCivico;

    public Indirizzo() {

    }

    public Indirizzo(String provincia, String citta, String via, String CAP) {
        this.provincia = provincia;
        this.citta = citta;
        this.via = via;
        this.CAP = CAP;
    }

    public Indirizzo(String provincia, String citta, String via, String CAP, int numeroCivico) {
        this.provincia = provincia;
        this.citta = citta;
        this.via = via;
        this.CAP = CAP;
        this.numeroCivico = numeroCivico;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }
}
