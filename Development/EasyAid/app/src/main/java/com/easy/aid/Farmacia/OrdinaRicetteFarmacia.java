package com.easy.aid.Farmacia;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.easy.aid.Medico.RichiesteRicetteMedico;
import com.easy.aid.Paziente.OrdinaRicettaPaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrdinaRicetteFarmacia extends AppCompatActivity {

    private ImageView back;
    private NetVariables global;
    private ListView listView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_ordina_ricette);

        global = (NetVariables) this.getApplication();
        back = findViewById(R.id.backRichiesteFarmacia);
        listView = findViewById(R.id.listFarmaciaOrdine);
        title = findViewById(R.id.listaOrdineFarmacia);

        checkAPI();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                finish();
            }
        });

        ReadRicette();
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

                                        if(R.getStatoRichiesta().equals("ORDINATA")){
                                            global.ricetteORDINATE.put(id,R);
                                        }

                                        global.prefs.edit().putString("readRicette", "1").apply();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrdinaRicetteFarmacia.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
                            }


                            ReadOrdini();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicetteFarmacia.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "9");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ReadOrdini() {

        global.ordini.clear();

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
                                Toast.makeText(OrdinaRicetteFarmacia.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            title.setText("NESSUN ORDINE");
                            CronoAdapter crono = new CronoAdapter(getApplicationContext(), false, null, global.ordini, global);
                            listView.setAdapter(crono);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicetteFarmacia.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "8");
                params.put("id", "1");
                params.put("cf", String.valueOf(global.farmacia.getId()));

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
