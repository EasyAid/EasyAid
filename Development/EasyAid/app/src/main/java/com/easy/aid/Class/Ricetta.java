package com.easy.aid.Class;

public class Ricetta {

    private int idRicetta, idMedico, idPaziente, idFarmaco, numeroScatole;
    private String descrizione, statoRichiesta, data, ora;
    private boolean  esenzioneReddito, esenzionePatologia;

    public Ricetta(int idRicetta, int idMedico, int idPaziente, int idFarmaco, int numeroScatole, String descrizione, String statoRichiesta, String data, String ora, boolean esenzioneReddito, boolean esenzionePatologia) {
        this.idRicetta = idRicetta;
        this.idMedico = idMedico;
        this.idPaziente = idPaziente;
        this.idFarmaco = idFarmaco;
        this.numeroScatole = numeroScatole;
        this.descrizione = descrizione;
        this.statoRichiesta = statoRichiesta;
        this.data = data;
        this.ora = ora;
        this.esenzioneReddito = esenzioneReddito;
        this.esenzionePatologia = esenzionePatologia;
    }

    public int getIdRicetta() {
        return idRicetta;
    }

    public void setIdRicetta(int idRicetta) {
        this.idRicetta = idRicetta;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(int idPaziente) {
        this.idPaziente = idPaziente;
    }

    public int getNumeroScatole() {
        return numeroScatole;
    }

    public void setNumeroScatole(int numeroScatole) {
        this.numeroScatole = numeroScatole;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
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

    public boolean isEsenzionePatologia() {
        return esenzionePatologia;
    }

    public void setEsenzionePatologia(boolean esenzionePatologia) {
        this.esenzionePatologia = esenzionePatologia;
    }

    public boolean isEsenzioneReddito() {
        return esenzioneReddito;
    }

    public void setEsenzioneReddito(boolean esenzioneReddito) {
        this.esenzioneReddito = esenzioneReddito;
    }

    public int getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(int idFarmaco) {
        this.idFarmaco = idFarmaco;
    }
}
