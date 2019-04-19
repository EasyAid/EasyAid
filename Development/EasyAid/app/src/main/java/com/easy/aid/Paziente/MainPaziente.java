package com.easy.aid.Paziente;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Paziente;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.InitialSplashScreen;
import com.easy.aid.MainActivity;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cristian
 *
 */

public class MainPaziente extends AppCompatActivity {

    private LinearLayout richiediVisRic, ordinaRicetta, impostazioni, cronologia;
    private int close=0;
    private TextView nomeCognome;

    private LinearLayout splash;
    private RelativeLayout noSplash;
    public NetVariables global;
    private Bundle bundle;
    private String sCF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_main);

        //CONTROLLA LE API DEL TELEFONO, SE MAGGIORI DI MARSHMELLOW MODIFICA IL COLORE DEL TESTO DELLA NOTIFICATION BAR IN CHIARO
        //ALTRIMENTI SE E' INFERIORE ALLE API 23 MODIFICA LA NOTIFICATION BAR IN COLORE SCURO (IN QUANTO NON PUO MODIFICARE IL COLORE DEL TESTO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(),R.color.colorAccent));
        }

        splash = findViewById(R.id.splashMainPaziente);
        noSplash = findViewById(R.id.noSplahMainPaziente);
        nomeCognome = findViewById(R.id.nomeCognomePaz);

        bundle = getIntent().getExtras();

        global = (NetVariables) this.getApplication();

        if(global.paziente != null){
            nomeCognome.setText(("BENVENUTO\n" + global.paziente.getNome().toUpperCase() + " " + global.paziente.getCognome().toUpperCase()));
        }else{
            splash.setVisibility(View.VISIBLE);
            noSplash.setVisibility(View.GONE);


            new CountDownTimer(1500, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    Read(bundle.getString("CF"));
                }

            }.start();

        }

        richiediVisRic = findViewById(R.id.richiediVisitaRicettaPaz);
        ordinaRicetta = findViewById(R.id.ordinaRicettaPaz);
        impostazioni = findViewById(R.id.impostazioniPaz);
        cronologia = findViewById(R.id.cronologiaPaz);


        richiediVisRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPaziente.this, RichiediVisitaRicettaPaziente.class);
                startActivity(intent);
            }
        });

        ordinaRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, OrdinaRicettaPaziente.class);
                startActivity(i);
            }
        });

        impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, ImpostazioniPaziente.class);
                startActivity(i);
            }
        });

        cronologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void Read(final String sCF) {

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
                                nomeCognome.setText(("BENVENUTO\n" + nome.toUpperCase() + " " + cognome.toUpperCase()));

                            }
                            readRicette();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cf", sCF);
                params.put("table", "0");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if(close==0){
            Toast.makeText(getApplicationContext(), "Premi un'altra volta per uscire", Toast.LENGTH_SHORT).show();
            close++;
        }else{
            finish();
        }
    }

    private void readRicette(){
        global.ricette.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, global.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        if(response!=null){
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString( "success");
                                JSONArray jsonArray = jsonObject.getJSONArray("read");

                                if (success.equals("1")){

                                    for(int i =0;i < jsonArray.length(); i++){

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String id  = object.getString("idricetta");
                                        String idMedico  = object.getString("idmedico");
                                        String idPaziente  = object.getString("idpaziente");
                                        String idFarmaco  = object.getString("idfarmaco");
                                        String numeroScatole  = object.getString("numeroscatole");
                                        String descrizione  = object.getString("descrizione");
                                        String esenzionePatologia  = object.getString("esenzionepatologia");
                                        String esenzioneReddito  = object.getString("esenzionereddito");
                                        String statoRichiesta  = object.getString("statorichiesta");
                                        String data  = object.getString("data");
                                        String ora  = object.getString("ora");

                                        global.ricette.add(new Ricetta(Integer.parseInt(id),Integer.parseInt(idMedico),Integer.parseInt(idPaziente), Integer.parseInt(idFarmaco),Integer.parseInt(numeroScatole), descrizione, statoRichiesta, data, ora, Boolean.parseBoolean(esenzioneReddito), Boolean.parseBoolean(esenzionePatologia)));
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }
                        }
                        splash.setVisibility(View.GONE);
                        noSplash.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "4");
                params.put("id", "0");
                params.put("cf", String.valueOf(global.paziente.getID()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
