package com.easy.aid.Class;

import android.app.Application;

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
    public Map<String, Farmaco> farmaci;


}
