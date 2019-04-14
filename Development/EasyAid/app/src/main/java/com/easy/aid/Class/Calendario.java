package com.easy.aid.Class;

import java.sql.Time;

public class Calendario {
    private Giorno[] settimana;
    Calendario(){
        settimana = new Giorno[7];
    }
    /*
    0 = Lunedì
    1 = Martedì
    2 = Mercoledì
    3 = Giovedì
    4 = Venerdì
    5 = Sabato
    6 = Domenica
     */
    static boolean verifyTimeString(String timestring) {
        if (timestring.length() != 5) return false;
        if (timestring.charAt(2) != ':') return false;
        for (int i = 0; i < 5; i++) {
            if(i == 2) i++;
            if(timestring.charAt(i) < '0' || timestring.charAt(i) > '9') return false;
        }
        int hour = Integer.parseInt(timestring.split(":")[0]);
        if (hour < 0 || hour >= 24) return false;

        int minute = Integer.parseInt(timestring.split(":")[1]);
        if (minute < 0 || minute >= 60) return false;
        return true;
    }
    static Time stringToTime(String timestring){ //trasforma una sequenza di caratteri hh:mm in variabile time
        return new Time(Integer.parseInt(timestring.split(":")[0]), Integer.parseInt(timestring.split(":")[1]), 0);
    }
}
