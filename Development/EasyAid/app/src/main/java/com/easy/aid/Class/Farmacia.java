package com.easy.aid.Class;

import java.security.PublicKey;

public class Farmacia {

    private int id;
    private String nomeFarmacia;
    private String telefono;
    private String email;
    private Indirizzo indirizzo;
    private String pwd;

    public Farmacia(){

    }

    public Farmacia(int id, String nomeFarmacia, String telefono, String email, Indirizzo indirizzo, String password) {

        setId(id);
        setNomeFarmacia(nomeFarmacia);
        setTelefono(telefono);
        setEmail(email);
        setIndirizzo(indirizzo);
        setPwd(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public void setNomeFarmacia(String nomeFarmacia) {
        this.nomeFarmacia = nomeFarmacia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }
}
