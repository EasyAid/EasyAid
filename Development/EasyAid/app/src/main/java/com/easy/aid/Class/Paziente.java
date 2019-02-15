package com.easy.aid.Class;

import android.widget.EditText;

public class Paziente {

    private int ID;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String codiceFiscale;
    private Indirizzo indirizzoNascita;
    private Indirizzo indirizzoResidenza;
    private String medicoBase, password, confermaPassword;

    public Paziente(int ID, String nome, String cognome, String dataNascita, String codiceFiscale, Indirizzo indirizzoNascita, Indirizzo indirizzoResidenza, String medicoBase, String password, String confermaPassword) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.indirizzoNascita = indirizzoNascita;
        this.indirizzoResidenza = indirizzoResidenza;
        this.medicoBase = medicoBase;
        this.password = password;
        this.confermaPassword = confermaPassword;
    }

    public Paziente() {
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Indirizzo getIndirizzoNascita() {
        return indirizzoNascita;
    }

    public void setIndirizzoNascita(Indirizzo indirizzoNascita) {
        this.indirizzoNascita = indirizzoNascita;
    }

    public Indirizzo getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(Indirizzo indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
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
