package com.easy.aid.Class;

import java.util.ArrayList;

public class Farmaco {

    private String nome;
    private ArrayList quatitaEuso;
    private ArrayList prezzo;


    public Farmaco(String nome, String quantitaEuso, String prezzo) {
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
}
