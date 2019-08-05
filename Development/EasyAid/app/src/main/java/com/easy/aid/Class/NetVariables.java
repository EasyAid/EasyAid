package com.easy.aid.Class;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class NetVariables extends Application {

    public static String URL = "99.80.72.24";
    public static String URL_LOGIN = "http://" + URL + "/login.php";
    public static String URL_READ = "http://" + URL + "/read.php";
    public static String URL_UPLOAD = "http://" + URL + "/upload.php";
    public static String URL_REGISTER = "http://" + URL + "/register.php";
    public static String URL_INSERT = "http://" + URL + "/insert.php";
    public static String URL_LOGIN_AES = "http://" + URL + "/loginAES.php";


    public DatabaseHelper db;

    public Map<String, Farmaco> farmaci;
    public Map<String, Farmaco> farmaciID;
    public List<Ricetta> ricette;
    public List<String> province;
    public List<String> siglaProvince;
    public List<String> siglaProvinceComuni;
    public List<String> comuni;
    public List<String> codiceComuni;

    public Medico medico;
    public Paziente paziente;
    public long mLastClickTime;

    public boolean checktime(){
        boolean time = (SystemClock.elapsedRealtime() - mLastClickTime) < 800;
        mLastClickTime = SystemClock.elapsedRealtime();
        return time;
    };


    public SharedPreferences prefs;

}
