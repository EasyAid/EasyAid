package com.easy.aid.Paziente;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.CustomAdapter;
import com.easy.aid.Class.Farmaco;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdinaRicettaPaziente extends AppCompatActivity implements View.OnClickListener {

    private NetVariables global;

    private boolean pagato = false;

    private RelativeLayout primo, secondo;
    private TextView indirizzo, totale;
    private List<Ricetta> ordine;
    private Button pagaOra;
    private Button invia;
    private Button continuaOrdine;
    private double tot = 0;
    private ListView listView, ordineList;
    private ImageView back;
    private int step;

    private LinearLayout splash;
    private RelativeLayout noSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_ordina_ricetta);

        checkAPI();
        global = (NetVariables) this.getApplication();

        ordine = new ArrayList<>();

        splash = findViewById(R.id.splashOrdinaPaziente);
        noSplash = findViewById(R.id.noSplahOrdinaPaziente);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        primo = findViewById(R.id.primoOrdinaFarmacia);
        secondo = findViewById(R.id.secondoOrdinaFarmacia);
        indirizzo = findViewById(R.id.indirizzoListaRicette);
        totale = findViewById(R.id.totaleListaRicette);
        pagaOra = findViewById(R.id.pagaOra);
        pagaOra.setOnClickListener(this);
        ordineList = findViewById(R.id.ordineListView);
        invia = findViewById(R.id.inviaOrdineRicettaPaziente);
        invia.setOnClickListener(this);
        continuaOrdine = findViewById(R.id.continuaOrdinaFarmacoPaziente);
        continuaOrdine.setOnClickListener(this);
        back = findViewById(R.id.backOrdinaRicPaz);
        listView = findViewById(R.id.listViewOrdinaFarmacoPaziente);

       if(global.prefs.getString("readRicette", null) != null && global.prefs.getString("readRicette", null).equals("1")){
            ReadRicetteLocal();
        }else{
            ReadRicette();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                finish();
            }
        });

    }

    private void ReadRicetteLocal(){

        global.ricette.clear();

        Cursor data = global.db.readData("5", "1");

        while(data.moveToNext()){
            String id  = data.getString(0);
            String idMedico  = data.getString(1);
            String idPaziente  = data.getString(2);
            String idFarmaco  = data.getString(3);
            String numeroScatole  = data.getString(4);
            String descrizione  = data.getString(5);
            String esenzionePatologia  = data.getString(6);
            String esenzioneReddito  = data.getString(7);
            String statoRichiesta  = data.getString(8);
            String dataR  = data.getString(9);
            String ora  = data.getString(10);

            Ricetta R = new Ricetta(Integer.parseInt(id),Integer.parseInt(idMedico),Integer.parseInt(idPaziente), Integer.parseInt(idFarmaco),Integer.parseInt(numeroScatole), descrizione, statoRichiesta, dataR, ora, Boolean.parseBoolean(esenzioneReddito), Boolean.parseBoolean(esenzionePatologia));
            global.ricette.add(R);

        }

        listView();
    }

    private void ReadRicette(){

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

                                        Ricetta R = new Ricetta(Integer.parseInt(id),Integer.parseInt(idMedico),Integer.parseInt(idPaziente), Integer.parseInt(idFarmaco),Integer.parseInt(numeroScatole), descrizione, statoRichiesta, data, ora, Boolean.parseBoolean(esenzioneReddito), Boolean.parseBoolean(esenzionePatologia));

                                        if(global.db.exist("Ricetta", R)){
                                            global.db.updateName("Ricetta", R);
                                        }else{
                                           global.db.addData("Ricetta", R);
                                        }

                                        global.ricette.add(R);

                                        global.prefs.edit().putString("readRicette", "1").apply();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrdinaRicettaPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }


                            listView();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicettaPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "5");
                params.put("id", "0");
                params.put("cf", String.valueOf(global.paziente.getID()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void listView(){

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), global.ricette, global.farmaciID, ordine, true, totale, global.farmaciID);
        listView.setAdapter(adapter);

        splash.setVisibility(View.GONE);
        noSplash.setVisibility(View.VISIBLE);
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void Invia(final Ricetta ricetta, final String date, final String time) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        if(response!=null){
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString( "success");

                                if (success.equals("1")){
                                    ModificaRicetta(ricetta.getIdRicetta());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrdinaRicettaPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }

                        }
                        Toast.makeText(getApplicationContext(), "Ordine inviata", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicettaPaziente.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "5");
                params.put("idfarmacia", "1");
                params.put("idricetta", String.valueOf(ricetta.getIdRicetta()));
                params.put("idpaziente", String.valueOf(ricetta.getIdPaziente()));
                if (pagato) {
                    params.put("pagato", "true");
                    params.put("totale", "0");
                } else {
                    params.put("pagato", "false");
                    params.put("totale", String.valueOf(tot));
                }
                params.put("data", date);
                params.put("ora", time);

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

    private void ModificaRicetta(final int IdRicetta) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        if(response!=null){
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString( "success");

                                if (success.equals("1")){

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrdinaRicettaPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Ordine inviata", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicettaPaziente.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("idricetta", String.valueOf(IdRicetta));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pagaOra:
                pagato = true;
                Toast.makeText(getApplicationContext(), "Ordine pagato", Toast.LENGTH_SHORT).show();
                break;

            case R.id.continuaOrdinaFarmacoPaziente:
                if (ordine.size() != 0) {

                    step = 1;
                    primo.setVisibility(View.GONE);
                    secondo.setVisibility(View.VISIBLE);

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(), global.ricette, global.farmaciID, ordine, false, totale, global.farmaciID);
                    ordineList.setAdapter(adapter);

                    for(int i=0;i<ordine.size();i++){
                        tot += Double.parseDouble(global.farmaciID.get(String.valueOf(ordine.get(i).getIdFarmaco())).getPrezzoString(String.valueOf(ordine.get(i).getIdFarmaco())));
                    }
                    totale.setText("TOTALE: " + tot + "€");

                    indirizzo.setText(("INDIRIZZO: " +global.paziente.getIndirizzoResidenza().getVia() + " " + global.paziente.getIndirizzoResidenza().getCitta()));
                } else {
                    Toast.makeText(getApplicationContext(), "Aggiungi una ricetta", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.inviaOrdineRicettaPaziente:


                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());
                df = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = df.format(c.getTime());

                for(int i=0;i<ordine.size();i++){
                    Invia(ordine.get(i), formattedDate, formattedTime);
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("pagOrdinePaziente", step);
        outState.putSerializable("ordineOrdinePaziente", (Serializable) ordine);
        outState.putSerializable("ricetteOrdinePaziente", (Serializable) global.ricette);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        step = savedInstanceState.getInt("pagOrdinePaziente");
        ordine = (List<Ricetta>) savedInstanceState.getSerializable("ordineOrdinePaziente");
        global.ricette = (List<Ricetta>) savedInstanceState.getSerializable("ricetteOrdinePaziente");
        switch (step) {
            case 0: {
                primo.setVisibility(View.VISIBLE);
                secondo.setVisibility(View.GONE);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), global.ricette, global.farmaciID, ordine, true, totale, global.farmaciID);
                ordineList.setAdapter(adapter);
                break;
            }
            case 1: {
                primo.setVisibility(View.GONE);
                secondo.setVisibility(View.VISIBLE);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), global.ricette, global.farmaciID, ordine, false, totale, global.farmaciID);
                ordineList.setAdapter(adapter);
                break;
            }
        }
    }
}
