package com.easy.aid.Paziente;

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
import com.easy.aid.Class.Ordine;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CronologiaPaziente extends AppCompatActivity {

    private ImageView back;
    private NetVariables global;
    private Button richieste, ordini;
    private ListView listView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_cronologia);


        checkAPI();
        global = (NetVariables) this.getApplication();
        back = findViewById(R.id.backCronologiaPaziente);
        richieste = findViewById(R.id.richiesteCronoPaziente);
        ordini = findViewById(R.id.ordiniCronoPaziente);
        listView = findViewById(R.id.listViewCrono);
        title = findViewById(R.id.listaCrono);


        richieste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richieste.setBackgroundColor(getResources().getColor(R.color.greenDark));
                ordini.setBackground(getDrawable(R.drawable.all_empty));

                richieste.setTextColor(getResources().getColor(R.color.white));
                ordini.setTextColor(getResources().getColor(R.color.greenDark));

                if (global.prefs.getString("readRicette", null) != null && global.prefs.getString("readRicette", null).equals("1")) {
                    ReadRicetteLocal();
                } else {
                    ReadRicette();
                }

                title.setText("LISTA RICETTE");
            }
        });

        ordini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordini.setBackgroundColor(getResources().getColor(R.color.greenDark));
                richieste.setBackground(getDrawable(R.drawable.all_empty));

                ordini.setTextColor(getResources().getColor(R.color.white));
                richieste.setTextColor(getResources().getColor(R.color.greenDark));

                title.setText("LISTA ORDINI");

                if (global.prefs.getString("readOrdini", null) != null && global.prefs.getString("readOrdini", null).equals("1")) {
                    ReadOrdiniLocal();
                } else {
                    ReadOrdini();
                }
            }
        });

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
        global.ricetteORDINATE.clear();

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
            if(!statoRichiesta.equals("ORDINATA")){
                global.ricette.add(R);
            }else{
                global.ricetteORDINATE.put(id, R);
            }
        }

        CronoAdapter crono = new CronoAdapter(getApplicationContext(), true, global.ricette, null, global);
        listView.setAdapter(crono);
    }

    private void ReadOrdiniLocal() {

        global.ordini.clear();

        Cursor data = global.db.readData("8", null);

        while (data.moveToNext()) {
            String idordine = data.getString(0);
            String idfarmacia = data.getString(1);
            String idpaziente = data.getString(2);
            String idricetta = data.getString(3);
            String pagato = data.getString(4);
            String totale = data.getString(5);
            String date = data.getString(6);
            String ora = data.getString(7);


            boolean find=false;
            for(int c=0;c<global.ordini.size();c++){
                if(global.ordini.get(c).getIdOrdine() == Integer.parseInt(idordine)){
                    global.ordini.get(c).addIdRicetta(Integer.parseInt(idricetta));
                    find=true;
                }
            }

            Ordine O = new Ordine(Integer.parseInt(idordine), Integer.parseInt(idpaziente), Integer.parseInt(idfarmacia), Integer.parseInt(pagato), Integer.parseInt(idricetta), date, ora, Double.parseDouble(totale));

            if(!find){
                global.ordini.add(O);
            }
        }

        CronoAdapter crono = new CronoAdapter(getApplicationContext(), false, null, global.ordini, global);
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

                                        if (global.db.exist("Ricetta", R)) {
                                            global.db.updateName("Ricetta", R);
                                        } else {
                                            global.db.addData("Ricetta", R);
                                        }

                                        if (!object.getString("statorichiesta").equals("ORDINATA")) {
                                            global.ricette.add(R);
                                        } else {
                                            global.ricetteORDINATE.put(id, R);
                                        }

                                        global.prefs.edit().putString("readRicette", "1").apply();
                                    }

                                    CronoAdapter crono = new CronoAdapter(getApplicationContext(), true, global.ricette, null, global);
                                    listView.setAdapter(crono);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CronologiaPaziente.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CronologiaPaziente.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
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

    private void ReadOrdini() {


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

                                        String idordine = object.getString("idordine");
                                        String idpaziente = object.getString("idpaziente");
                                        String idricetta = object.getString("idricetta");
                                        String idfarmacia = object.getString("idfarmacia");
                                        String pagato = object.getString("pagato");
                                        String totale = object.getString("totale");
                                        String data = object.getString("data");
                                        String ora = object.getString("ora");

                                        boolean find=false;
                                        for(int c=0;c<global.ordini.size();c++){
                                            if(global.ordini.get(c).getData().equals(data)&&global.ordini.get(c).getOra().equals(ora)&&global.ordini.get(c).getIdPaziente() == Integer.parseInt(idpaziente)){
                                                global.ordini.get(c).addIdRicetta(Integer.parseInt(idricetta));
                                                find=true;
                                            }
                                        }
                                        Ordine O = new Ordine(Integer.parseInt(idordine), Integer.parseInt(idpaziente), Integer.parseInt(idfarmacia), Integer.parseInt(pagato), Integer.parseInt(idricetta), data, ora, Double.parseDouble(totale));
                                        if(!find){
                                            global.ordini.add(O);
                                        }
                                    }

                                    for(int i=0;i<global.ordini.size();i++){
                                        Ordine O = global.ordini.get(i);
                                        if(global.db.exist("Ordine", O)){
                                            global.db.updateName("Ordine", O);
                                        }else{
                                            global.db.addData("Ordine", O);
                                        }
                                    }

                                    //listView
                                    CronoAdapter crono = new CronoAdapter(getApplicationContext(), false, null, global.ordini, global);
                                    listView.setAdapter(crono);

                                    global.prefs.edit().putString("readOrdini", "1").apply();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CronologiaPaziente.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CronologiaPaziente.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "8");
                params.put("id", "0");
                params.put("cf", String.valueOf(global.paziente.getID()));

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
