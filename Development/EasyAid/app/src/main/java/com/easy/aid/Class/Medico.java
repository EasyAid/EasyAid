package com.easy.aid.Class;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Medico {
    private int ID;
    private String nome;
    private String cognome;
    private String dataNascita;
    private boolean sesso; //1 = maschio, 0 = femmina
    private String codiceFiscale;
    private Indirizzo luogoNascita;
    private Indirizzo studio;
    private String password;
    private String email;
    private String telefono;

    public Medico(String nome, String cognome, String dataNascita, boolean sesso, String codiceFiscale, Indirizzo luogoNascita, Indirizzo studio, String password, String email, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.codiceFiscale = codiceFiscale;
        this.luogoNascita = luogoNascita;
        this.studio = studio;
        this.password = password;
        setEmail(email);
        setTelefono(telefono);
    }

    public Medico(int ID, String nome, String cognome, String dataNascita, boolean sesso, String codiceFiscale, Indirizzo luogoNascita, Indirizzo studio, String password, String email, String telefono) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.codiceFiscale = codiceFiscale;
        this.luogoNascita = luogoNascita;
        this.studio = studio;
        this.password = password;
        this.email = email;
        this.telefono = telefono;
    }

    public Medico() {
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

    public void setSesso(boolean sesso) {
        this.sesso = sesso;
    }

    public boolean getSesso() {
        return sesso;
    }

    public String getStringSesso() {
        if (sesso) return "Maschio";
        return "Femmina";
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Indirizzo getIndirizzoNascita() {
        return luogoNascita;
    }

    public void setIndirizzoNascita(Indirizzo luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public Indirizzo getIndirizzoStudio() {
        return studio;
    }

    public void setIndirizzoStudio(Indirizzo studio) {
        this.studio = studio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email = email;
            return true;
        } else
            return false;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean setTelefono(String telefono) {
        if (Patterns.PHONE.matcher(telefono).matches()) {
            this.telefono = telefono;
            return true;
        } else
            return false;
    }
}
