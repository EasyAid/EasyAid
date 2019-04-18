package com.easy.aid.Class;

import java.util.ArrayList;

public class Farmaco {

    private ArrayList id;
    private String nome;
    private ArrayList quatitaEuso;
    private ArrayList prezzo;

    public Farmaco(String id, String nome, String quantitaEuso, String prezzo) {
        this.id = new ArrayList();
        this.id.add(id);
        this.nome = nome;
        this.quatitaEuso = new ArrayList();
        this.quatitaEuso.add(quantitaEuso);
        this.prezzo = new ArrayList();
        this.prezzo.add(prezzo);
    }

    public ArrayList getQuatitaEuso() {
        return quatitaEuso;
    }

    public void setQuatitaEuso(String quatitaEuso) {
        this.quatitaEuso.add(quatitaEuso);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo.add(prezzo);
    }

    public ArrayList getId() {
        return id;
    }

    public void setId(String id) {
        this.id.add(id);
    }
}
