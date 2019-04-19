package com.easy.aid.Class;

import java.util.ArrayList;

public class Farmaco {

    private ArrayList<String> id;
    private String nome;
    private ArrayList<String> quatitaEuso;
    private ArrayList<String> prezzo;

    public Farmaco(String id, String nome, String quantitaEuso, String prezzo) {
        this.id = new ArrayList<String>();
        this.id.add(id);
        this.nome = nome;
        this.quatitaEuso = new ArrayList<String>();
        this.quatitaEuso.add(quantitaEuso);
        this.prezzo = new ArrayList<String>();
        this.prezzo.add(prezzo);
    }

    public ArrayList getQuatitaEuso() {
        return quatitaEuso;
    }


    public String getQuatitaEusoString(String searchId) {
        for(int i=0;i<id.size();i++){
            if(id.get(i).equals(searchId)){
                return quatitaEuso.get(i);
            }
        }
        return null;
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

    public String getPrezzoString(String searchId) {
        for(int i=0;i<id.size();i++){
            if(id.get(i).equals(searchId)){
                return prezzo.get(i);
            }
        }
        return null;
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
