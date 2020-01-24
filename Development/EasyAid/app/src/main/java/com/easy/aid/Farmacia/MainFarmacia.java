package com.easy.aid.Farmacia;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.easy.aid.Class.Farmacia;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Paziente;
import com.easy.aid.MainActivity;
import com.easy.aid.Paziente.MainPaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainFarmacia extends AppCompatActivity {

    private LinearLayout ordini;
    public NetVariables global;
    private Bundle bundle;
    private TextView nomeCognome;
    private LinearLayout splash;
    private RelativeLayout noSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_main);

        global = (NetVariables) this.getApplication();

        checkAPI();

        ordini = findViewById(R.id.richiestaFarmacoFarm);
        nomeCognome = findViewById(R.id.nomeCognomeFarm);
        splash = findViewById(R.id.splashMainFarmacia);
        noSplash = findViewById(R.id.noSplahMainFarmacia);
        bundle = getIntent().getExtras();

        ordini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFarmacia.this, OrdinaRicetteFarmacia.class));
            }
        });

        if (global.paziente != null) {
            nomeCognome.setText(global.farmacia.getNomeFarmacia().toUpperCase());

        } else {
            splash.setVisibility(View.VISIBLE);
            noSplash.setVisibility(View.GONE);


            new CountDownTimer(1500, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    ReadDaTi(bundle.getString("Email"));
                }

            }.start();

        }

    }


    private void ReadDaTi(final String Email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, global.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {

                                JSONObject object = jsonArray.getJSONObject(0);
                                int id = object.getInt("id");
                                String nomeFarmacia = object.getString("nomefarmacia");
                                String telefono = object.getString("telefono");
                                String email = object.getString("email");
                                String provincia = object.getString("provincia");
                                String citta = object.getString("citta");
                                String via = object.getString("via");
                                String password = object.getString("password");
                                Indirizzo indirizzo = new Indirizzo(provincia, citta, via, null);

                                global.farmacia = new Farmacia(id, nomeFarmacia, telefono, email, indirizzo, password);

                                nomeCognome.setText(global.farmacia.getNomeFarmacia().toUpperCase());
                            }


                            splash.setVisibility(View.GONE);
                            noSplash.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainFarmacia.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainFarmacia.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cf", Email);
                params.put("table", "2");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkAPI(){
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
    }
}
