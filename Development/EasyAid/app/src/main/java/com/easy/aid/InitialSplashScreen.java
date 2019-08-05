package com.easy.aid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.CountDownTimer;
import android.print.PageRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.DatabaseHelper;
import com.easy.aid.Class.Farmaco;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.Medico;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Paziente;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.Farmacia.MainFarmacia;
import com.easy.aid.Medico.MainMedico;
import com.easy.aid.Paziente.MainPaziente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InitialSplashScreen extends AppCompatActivity {

    private NetVariables global;
    private Intent intent;
    private String settore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //CONTROLLA LE API DEL TELEFONO, SE MAGGIORI DI MARSHMELLOW MODIFICA IL COLORE DEL TESTO DELLA NOTIFICATION BAR IN CHIARO
        //ALTRIMENTI SE E' INFERIORE ALLE API 23 MODIFICA LA NOTIFICATION BAR IN COLORE SCURO (IN QUANTO NON PUO MODIFICARE IL COLORE DEL TESTO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(), R.color.colorAccent));
        }

        global = (NetVariables) this.getApplication();
        global.prefs = this.getSharedPreferences(
                "com.easy.aid", Context.MODE_PRIVATE);

        global.farmaci = new HashMap<>();
        global.farmaciID = new HashMap<>();
        global.ricette = new ArrayList<>();
        global.province = new ArrayList<String>();
        global.siglaProvince = new ArrayList<String>();
        global.siglaProvinceComuni = new ArrayList<String>();
        global.comuni = new ArrayList<String>();
        global.codiceComuni = new ArrayList<String>();
        global.db = new DatabaseHelper(this);
        global.prefs.edit().putString("readRicette", "0").apply();

        leggiProvince();
        leggiFarmaci();


    }

    private void Connessione(){
        if (global.prefs.getString("CF", null) != null) {

            if (global.prefs.getString("settore", null).equals("Paziente")) {
                intent = new Intent(InitialSplashScreen.this, MainPaziente.class);
                settore = "Paziente";

            } else if (global.prefs.getString("settore", null).equals("Medico")) {
                intent = new Intent(InitialSplashScreen.this, MainMedico.class);
                settore = "Medico";

            } else if (global.prefs.getString("settore", null).equals("Farmacia")) {
                intent = new Intent(InitialSplashScreen.this, MainFarmacia.class);
                settore = "Farmacia";

            }

            Read();
        } else {

            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    startActivity(new Intent(InitialSplashScreen.this, MainActivity.class));
                    finish();
                }

            }.start();
        }
    }

    private void Read(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, global.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                JSONObject object = jsonArray.getJSONObject(0);

                                if(settore.equals("Paziente")){

                                    int id = object.getInt("id");
                                    String nome = object.getString("nome");
                                    String cognome = object.getString("cognome");
                                    String dataNascita = object.getString("datanascita");
                                    String codiceFiscale = object.getString("codicefiscale");
                                    String provinciaNascita = object.getString("provincianascita");
                                    String cittaNascita = object.getString("cittanascita");
                                    String provinciaResidenza = object.getString("provinciaresidenza");
                                    String cittaResidenza = object.getString("cittaresidenza");
                                    String viaResidenza = object.getString("viaresidenza");
                                    Indirizzo nascita = new Indirizzo(provinciaNascita,cittaNascita,null,null);
                                    Indirizzo residenza = new Indirizzo(provinciaResidenza,cittaResidenza,viaResidenza,null);
                                    global.paziente = new Paziente(id, nome,cognome,dataNascita,codiceFiscale,nascita,residenza,null,null);

                                }else if(settore.equals("Medico")){

                                    int id = object.getInt("id");
                                    String resultCf = object.getString("codicefiscale");
                                    String resultPassword = object.getString("password");
                                    String resultNome = object.getString("nome");
                                    String resultCognome = object.getString("cognome");
                                    String resultDatanascita = object.getString("datanascita");
                                    String resultSesso = object.getString("sesso");
                                    String resultProvincianascita = object.getString("provincianascita");
                                    String resultCittanascita = object.getString("cittanascita");
                                    String resultProvinciastudio = object.getString("provinciastudio");
                                    String resultCittastudio = object.getString("cittastudio");
                                    String resultViastudio = object.getString("viastudio");
                                    String resultEmail = object.getString("email");
                                    String resultTelefono= object.getString("telefono");

                                    Indirizzo resultLuogonascita = new Indirizzo(resultProvincianascita, resultCittanascita);
                                    Indirizzo resultLuogostudio = new Indirizzo(resultProvinciastudio, resultCittastudio);

                                    global.medico = new Medico(id, resultNome, resultCognome, resultDatanascita,
                                            global.medico.getStringSesso(resultSesso),
                                            resultCf, resultLuogonascita, resultLuogostudio,
                                            resultPassword, resultEmail, resultTelefono);

                                }else if(settore.equals("Farmacia")){


                                    new CountDownTimer(1000, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                        }

                                        public void onFinish() {
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }.start();
                                }

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(InitialSplashScreen.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InitialSplashScreen.this, "Errore di connessione" , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                if(settore.equals("Paziente")) params.put("table", "0");
                else if(settore.equals("Medico")) params.put("table", "1");
                else if(settore.equals("Farmacia"))params.put("table", "2");

                params.put("cf", global.prefs.getString("CF", null));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void leggiFarmaci(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, global.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for(int i =0;i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String id  = object.getString("idfarmaco");
                                    String nome  = object.getString("nomefarmaco");
                                    String confezione  = object.getString("confezione");
                                    String prezzo  = object.getString("prezzo");
                                    prezzo = prezzo.replace(",",".");

                                    if(global.farmaci.containsKey(nome)){
                                        global.farmaci.get(nome).setId(id);
                                        global.farmaci.get(nome).setQuatitaEuso(confezione);
                                        global.farmaci.get(nome).setPrezzo(prezzo);
                                    }else{
                                        global.farmaci.put(nome,new Farmaco(id,nome,confezione,prezzo));
                                    }
                                    global.farmaciID.put(id,new Farmaco(id,nome,confezione,prezzo));
                                }
                                Connessione();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(InitialSplashScreen.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InitialSplashScreen.this, "Errore di connessione" , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "3");
                params.put("cf", "test");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void leggiProvince(){
        BufferedReader reader = null;
        try {

            String mLine;
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("province.txt"), StandardCharsets.UTF_8));

            // do reading, usually loop until end of file reading

            while ((mLine = reader.readLine()) != null) {


                String provincia = mLine;
                String[] parts = provincia.split(";");
                global.province.add(parts[0]);
                global.siglaProvince.add(parts[1]);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        leggiComuni();
    }

    private void leggiComuni(){
        BufferedReader reader = null;
        try {

            String mLine;
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("comuni.txt"), StandardCharsets.UTF_8));

            // do reading, usually loop until end of file reading

            while ((mLine = reader.readLine()) != null) {


                String comune = mLine;
                String[] parts = comune.split(";");
                global.siglaProvinceComuni.add(parts[0]);
                global.comuni.add(parts[1]);
                global.codiceComuni.add(parts[2]);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
}
