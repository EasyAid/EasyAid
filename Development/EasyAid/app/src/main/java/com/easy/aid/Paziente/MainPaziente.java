package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
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
import com.easy.aid.Class.Paziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static String URL_LOGIN = "http://99.80.72.24/read.php";

    public Paziente paziente;
    private String sCF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_main);

        Bundle bundle = getIntent().getExtras();
        sCF = bundle.getString("CF");

        Read(sCF);

        richiediVisRic = (LinearLayout) findViewById(R.id.richiediVisitaRicettaPaz);
        ordinaRicetta = (LinearLayout) findViewById(R.id.ordinaRicettaPaz);
        impostazioni = (LinearLayout) findViewById(R.id.impostazioniPaz);
        cronologia = (LinearLayout) findViewById(R.id.cronologiaPaz);
        nomeCognome = (TextView) findViewById(R.id.nomeCognomePaz);

        richiediVisRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, RichiediVisitaRicettaPaziente.class);
                startActivity(i);
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

            }
        });

        cronologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void Read(final String sCF) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for(int i=0; i<1;i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("id");
                                    String nome = object.getString("nome");
                                    String cognome = object.getString("cognome");
                                    String dataNascita = object.getString("dataNascita");
                                    String codiceFiscale = object.getString("codiceFiscale");
                                    String provinciaNascita = object.getString("provinciaNascita");
                                    String cittaNascita = object.getString("cittaNascita");
                                    String provinciaResidenza = object.getString("provinciaResidenza");
                                    String cittaResidenza = object.getString("cittaResidenza");
                                    String viaResidenza = object.getString("viaResidenza");
                                    Indirizzo nascita = new Indirizzo(provinciaNascita,cittaNascita,null,null);
                                    Indirizzo residenza = new Indirizzo(provinciaResidenza,cittaResidenza,viaResidenza,null);
                                    paziente = new Paziente(id, nome,cognome,dataNascita,codiceFiscale,nascita,residenza,null,null);
                                    nomeCognome.setText(nome + " " + cognome);
                                }
                            }
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cf", sCF);
                params.put("table", "Paziente");
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
}
