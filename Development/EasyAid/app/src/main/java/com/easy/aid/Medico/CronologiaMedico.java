package com.easy.aid.Medico;

import android.database.Cursor;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.CronoAdapter;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.Paziente.CronologiaPaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CronologiaMedico extends AppCompatActivity {

    private ImageView back;
    private NetVariables global;
    private ListView listView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_cronologia);

        checkAPI();
        global = (NetVariables) this.getApplication();
        back = findViewById(R.id.backCronoMed);
        listView = findViewById(R.id.listMedicoCrono);
        title = findViewById(R.id.listaCronoMedico);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                finish();
            }
        });

        if (global.prefs.getString("readRicette", null) != null && global.prefs.getString("readRicette", null).equals("1")) {
            ReadRicetteLocal();
        } else {
            ReadRicette();
        }
    }

    private void ReadRicetteLocal() {

        global.ricette.clear();

        Cursor data = global.db.readData("4", null);

        while (data.moveToNext()) {
            String id = data.getString(0);
            String idMedico = data.getString(1);
            String idPaziente = data.getString(2);
            String idFarmaco = data.getString(3);
            String numeroScatole = data.getString(4);
            String descrizione = data.getString(5);
            String esenzionePatologia = data.getString(6);
            String esenzioneReddito = data.getString(7);
            String statoRichiesta = data.getString(8);
            String dataR = data.getString(9);
            String ora = data.getString(10);

            Ricetta R = new Ricetta(Integer.parseInt(id), Integer.parseInt(idMedico), Integer.parseInt(idPaziente), Integer.parseInt(idFarmaco), Integer.parseInt(numeroScatole), descrizione, statoRichiesta, dataR, ora, Boolean.parseBoolean(esenzioneReddito), Boolean.parseBoolean(esenzionePatologia));

            global.ricette.add(R);
        }

        CronoAdapter crono = new CronoAdapter(getApplicationContext(), true, global.ricette, null, global);
        listView.setAdapter(crono);
    }


    private void ReadRicette() {

        global.ricette.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, global.URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        if (response != null) {
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("read");

                                if (success.equals("1")) {

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String id = object.getString("idricetta");
                                        String idMedico = object.getString("idmedico");
                                        String idPaziente = object.getString("idpaziente");
                                        String idFarmaco = object.getString("idfarmaco");
                                        String numeroScatole = object.getString("numeroscatole");
                                        String descrizione = object.getString("descrizione");
                                        String esenzionePatologia = object.getString("esenzionepatologia");
                                        String esenzioneReddito = object.getString("esenzionereddito");
                                        String statoRichiesta = object.getString("statorichiesta");
                                        String data = object.getString("data");
                                        String ora = object.getString("ora");

                                        Ricetta R = new Ricetta(Integer.parseInt(id), Integer.parseInt(idMedico), Integer.parseInt(idPaziente), Integer.parseInt(idFarmaco), Integer.parseInt(numeroScatole), descrizione, statoRichiesta, data, ora, Boolean.parseBoolean(esenzioneReddito), Boolean.parseBoolean(esenzionePatologia));

                                        if (object.getString("statorichiesta").equals("ORDINATA")) {
                                            R.setStatoRichiesta("ACCETTATA");
                                        }

                                        if (global.db.exist("Ricetta", R)) {
                                            global.db.updateName("Ricetta", R);
                                        } else {
                                            global.db.addData("Ricetta", R);
                                        }

                                        global.ricette.add(R);

                                        global.prefs.edit().putString("readRicette", "1").apply();
                                    }

                                    CronoAdapter crono = new CronoAdapter(getApplicationContext(), true, global.ricette, null, global);
                                    listView.setAdapter(crono);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CronologiaMedico.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CronologiaMedico.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "4");
                params.put("id", "1");
                params.put("cf", String.valueOf(global.medico.getID()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkAPI() {
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
