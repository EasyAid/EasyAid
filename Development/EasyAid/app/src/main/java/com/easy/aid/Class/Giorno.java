package com.easy.aid.Class;

import java.sql.Time;

public class Giorno {
    private boolean lavoro;
    private Time inizio1;
    private Time fine1;
    private boolean pausa;
    private Time inizio2;
    private Time fine2;

    public Giorno(boolean lavoro, Time inizio1, Time fine1, boolean pausa, Time inizio2, Time fine2) {
        this.lavoro = lavoro;
        this.inizio1 = inizio1;
        this.fine1 = fine1;
        this.pausa = pausa;
        this.inizio2 = inizio2;
        this.fine2 = fine2;
    }

    public void setLavoro(boolean lavoro) {
        this.lavoro = lavoro;
    }

    public boolean getLavoro() {
        return lavoro;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public boolean getPausa() {
        return pausa;
    }

    public Time getInizio1() {
        return inizio1;
    }

    public boolean setTurno1(Time inizio, Time fine) {
        if (fine.equals(inizio)) {
            return false;
        }
        if (fine.before(inizio)) {
            this.inizio1 = fine;
            this.fine1 = inizio;
        } else {
            this.inizio1 = inizio;
            this.fine1 = fine;
        }
        return true;
    }

    public boolean setTurno2(Time inizio, Time fine) {
        if (fine.equals(inizio)) {
            return false;
        }
        if (this.fine1.after(inizio) || this.fine1.after(fine)) {
            return false;
        }
        if (fine.before(inizio)) {
            this.fine1 = inizio;
            this.inizio1 = fine;
        } else {
            this.inizio2 = inizio;
            this.fine2 = fine;
        }
        return true;
    }
}
