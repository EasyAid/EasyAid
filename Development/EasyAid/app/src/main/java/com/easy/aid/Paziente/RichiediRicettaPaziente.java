package com.easy.aid.Paziente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**

 @author Cristian

 */

public class RichiediRicettaPaziente extends AppCompatActivity {

    private TextView nomeCognomePaz, nomeCognomeMed, usoFarmaco, prezzoFarmaco;
    private EditText descPatologia;
    private AutoCompleteTextView autoComp;
    private NetVariables global;
    private Spinner dropdown;
    private boolean set = false;
    private Button invia;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_ricetta);

        checkAPI();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        global  = ((NetVariables) this.getApplication());

        nomeCognomePaz  = findViewById(R.id.nomeCognomePazienteRichiediRicettaPaziente);
        nomeCognomeMed  = findViewById(R.id.nomeCognomeMedicoRichiediRicettaPaz);
        dropdown        = findViewById(R.id.usoFarmacoRichiediRicettaPaz);
        prezzoFarmaco   = findViewById(R.id.prezzoFarmacoRichiediRicettaPaz);
        invia           = findViewById(R.id.inviaRichiestaPaz);
        descPatologia = findViewById(R.id.descPatologiaRichiediRicettaPaz);
        back = findViewById(R.id.backRichiediRicPaz);

        nomeCognomePaz.setText(Html.fromHtml(nomeCognomePaz.getText().toString() + "<br><b>" + global.paziente.getCognome().toUpperCase() +" " + global.paziente.getNome().toUpperCase() + "<b>"));
        nomeCognomeMed.setText(Html.fromHtml(nomeCognomeMed.getText().toString() + "<br><b>" + global.medico.getCognome().toUpperCase() +" " + global.medico.getNome().toUpperCase() + "<b>"));

        Set<String> keys = global.farmaci.keySet();
        String[] nomeFarmaci = keys.toArray(new String[keys.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, nomeFarmaci);

        autoComp = findViewById(R.id.autoCompleteNomeFarmacoRichiediRicettaPaz);
        autoComp.setThreshold(1);//will start working from first character
        autoComp.setAdapter(adapter);
        autoCompleteUsoEQuantita(-2);

        autoComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteUsoEQuantita(0);
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(global.farmaci.containsKey(autoComp.getText().toString())){
                    autoCompleteUsoEQuantita(0);
                    hideKeyboard();
                }else{
                    autoCompleteUsoEQuantita(-2);
                }
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!set) {
                    autoCompleteUsoEQuantita(-1);
                }else{
                    set = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!autoComp.getText().toString().isEmpty()&&!descPatologia.getText().toString().isEmpty()&&global.farmaci.containsKey(autoComp.getText().toString())){
                    Invia();
                }else{
                    if(autoComp.getText().toString().isEmpty()){
                        autoComp.setError("Inserisci farmaco");
                    }else if(!global.farmaci.containsKey(autoComp.getText().toString())){
                        autoComp.setText("");
                        autoComp.setError("Inserisci un farmaco valido");
                    }

                    if(descPatologia.getText().toString().isEmpty()){
                        descPatologia.setError("Inserisci una descrizione");
                    }
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

    private void autoCompleteUsoEQuantita(int pos){

        if(pos == 0){
            set = true;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, global.farmaci.get(autoComp.getText().toString()).getQuatitaEuso());
            dropdown.setAdapter(adapter);
            String supp  = "PREZZO: <b>" + global.farmaci.get(autoComp.getText().toString()).getPrezzo().get(dropdown.getSelectedItemPosition()) + "€</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else if(pos == -1) {
            String supp  = "PREZZO: <b>" + global.farmaci.get(autoComp.getText().toString()).getPrezzo().get(dropdown.getSelectedItemPosition()) + "€</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else {
            set = true;
            String[] nullo = {"UTILIZZO E QUANTITÀ"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nullo);
            dropdown.setAdapter(adapter);
            prezzoFarmaco.setText("PREZZO:");
        }
    }


    private void Invia() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());
        df = new SimpleDateFormat("HH:mm:ss");
        final String formattedTime = df.format(c.getTime());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        global.prefs.edit().putString("readRicette", "0").apply();
                        Toast.makeText(getApplicationContext(), "Richiesta inviata", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RichiediRicettaPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //todo migliora
                params.put("table", "3");
                params.put("idmedico", "2");
                params.put("idpaziente", String.valueOf(global.paziente.getID()));
                params.put("idfarmaco", String.valueOf(global.farmaci.get(autoComp.getText().toString()).getId().get(dropdown.getSelectedItemPosition())));
                params.put("numeroscatole", "1");
                params.put("descrizione", descPatologia.getText().toString());
                params.put("esenzionepatologia", "false");
                params.put("esenzionereddito", "false");
                params.put("statorichiesta", "IN ATTESA");
                params.put("data", formattedDate);
                params.put("ora", formattedTime);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
