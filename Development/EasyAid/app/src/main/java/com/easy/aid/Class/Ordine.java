package com.easy.aid.Class;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Ordine {

    private int IdOrdine, IdPaziente, IdFarmacia, pagato;
    private List<Integer> IdRicetta;
    private String data, ora;
    private double totale;


    public Ordine(int idordine, int idpaziente, int idfarmacia, int pagato, int idricetta, String data, String ora, double totale){
        IdOrdine = idordine;
        IdPaziente = idpaziente;
        IdFarmacia = idfarmacia;
        this.pagato = pagato;
        this.totale = totale;
        this.data = data;
        this.ora = ora;


        IdRicetta = new ArrayList<>();
        IdRicetta.add(idricetta);
    }

    public int getIdOrdine() {
        return IdOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        IdOrdine = idOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public int getPagato() {
        return pagato;
    }

    public void setPagato(int pagato) {
        this.pagato = pagato;
    }

    public int getIdFarmacia() {
        return IdFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        IdFarmacia = idFarmacia;
    }

    public int getIdPaziente() {
        return IdPaziente;
    }

    public void setIdPaziente(int idPaziente) {
        IdPaziente = idPaziente;
    }

    public List<Integer> getIdRicetta() {
        return IdRicetta;
    }

    public void setIdRicetta(List<Integer> idRicetta) {
        IdRicetta = idRicetta;
    }

    public void addIdRicetta(Integer idRicetta) {
        IdRicetta.add(idRicetta);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
}
