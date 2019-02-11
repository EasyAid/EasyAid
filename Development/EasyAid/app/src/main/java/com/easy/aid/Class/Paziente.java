package com.easy.aid.Class;

import android.widget.EditText;

public class Paziente {

    private int ID;
    private String nome, cognome, dataNascita, provinciaNascita, cittaNascita, viaNascita;
    private String codiceFiscale, provinciaResidenza, cittaResidenza, viaResidenza;
    private String medicoBase, password, confermaPassword;

    public Paziente() {

    }

    public Paziente(int ID, String nome, String cognome, String dataNascita, String provinciaNascita, String cittaNascita, String viaNascita, String codiceFiscale, String provinciaResidenza, String cittaResidenza, String viaResidenza, String medicoBase, String password, String confermaPassword) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.provinciaNascita = provinciaNascita;
        this.cittaNascita = cittaNascita;
        this.viaNascita = viaNascita;
        this.codiceFiscale = codiceFiscale;
        this.provinciaResidenza = provinciaResidenza;
        this.cittaResidenza = cittaResidenza;
        this.viaResidenza = viaResidenza;
        this.medicoBase = medicoBase;
        this.password = password;
        this.confermaPassword = confermaPassword;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getProvinciaNascita() {
        return provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita) {
        this.provinciaNascita = provinciaNascita;
    }

    public String getCittaNascita() {
        return cittaNascita;
    }

    public void setCittaNascita(String cittaNascita) {
        this.cittaNascita = cittaNascita;
    }

    public String getViaNascita() {
        return viaNascita;
    }

    public void setViaNascita(String viaNascita) {
        this.viaNascita = viaNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

    public String getViaResidenza() {
        return viaResidenza;
    }

    public void setViaResidenza(String viaResidenza) {
        this.viaResidenza = viaResidenza;
    }

    public String getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(String medicoBase) {
        this.medicoBase = medicoBase;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }
}
