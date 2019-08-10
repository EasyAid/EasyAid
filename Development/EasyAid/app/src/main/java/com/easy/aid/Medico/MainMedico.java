package com.easy.aid.Medico;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.Medico;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Paziente;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.InitialSplashScreen;
import com.easy.aid.MainActivity;
import com.easy.aid.Paziente.MainPaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.easy.aid.Class.NetVariables.URL_LOGIN;
import static com.easy.aid.Class.NetVariables.URL_READ;

/**
 * @author pagina principale delle azioni di un medico
 */

public class MainMedico extends AppCompatActivity {

    ImageView back;
    private TextView nomeCognome;
    private NetVariables global;
    private LinearLayout richiesteRicette;
    private LinearLayout impostazioni;
    private LinearLayout cronologia;
    private LinearLayout calendario;


    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_main);


        nomeCognome = findViewById(R.id.nomeCognomeMed);

        global = (NetVariables) this.getApplication();
        Bundle bundle = getIntent().getExtras();

        if(global.medico != null){
            nomeCognome.setText(("BENVENUTO\n" + global.medico.getNome().toUpperCase() + " " + global.medico.getCognome().toUpperCase()));
        }else{

            Read(bundle.getString("CF"));

            new CountDownTimer(1000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                }

            }.start();

        }


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

        richiesteRicette = (LinearLayout) findViewById(R.id.richiestaRicettaMed);

        richiesteRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, RichiesteRicetteMedico.class);
                startActivity(intent);
            }
        });

        impostazioni = (LinearLayout) findViewById(R.id.impostazioniMed);
        impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, ImpostazioniMedico.class);
                startActivity(intent);
            }
        });

        cronologia = (LinearLayout) findViewById(R.id.cronologiaMed);
        cronologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, CronologiaMedico.class);
            }
        });

        calendario = (LinearLayout) findViewById(R.id.calendarioMed);
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, CalendarioMedico.class);
            }

   });


    }

 private void Read(final String sCF) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
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

                                nomeCognome.setText(("BENVENUTO\n" + resultNome.toUpperCase() + " " + resultCognome.toUpperCase()));
                                readRicette();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainMedico.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainMedico.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cf", sCF);
                params.put("table", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                Toast.makeText(MainMedico.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainMedico.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "4");
                params.put("id", "1");
                params.put("cf", "2");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
