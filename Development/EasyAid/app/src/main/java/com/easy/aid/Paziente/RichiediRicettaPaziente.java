package com.easy.aid.Paziente;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
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
    private NetVariables c;
    private Spinner dropdown;
    private boolean set = false;
    private Button invia;
    private int close;
    private String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_ricetta);

        c  = ((NetVariables) this.getApplication());

        nomeCognomePaz  = (TextView) findViewById(R.id.nomeCognomePazienteRichiediRicettaPaz);
        nomeCognomeMed  = (TextView) findViewById(R.id.nomeCognomeMedicoRichiediRicettaPaz);
        dropdown        = (Spinner) findViewById(R.id.usoFarmacoRichiediRicettaPaz);
        prezzoFarmaco   = (TextView) findViewById(R.id.prezzoFarmacoRichiediRicettaPaz);
        invia           = (Button) findViewById(R.id.inviaRichiestaPaz);
        descPatologia = (EditText) findViewById(R.id.descPatologiaRichiediRicettaPaz);

        Set<String> keys = c.farmaci.keySet();
        String[] nomeFarmaci = keys.toArray(new String[keys.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, nomeFarmaci);

        autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteNomeFarmacoRichiediRicettaPaz);
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
                if(c.farmaci.containsKey(autoComp.getText().toString())){
                    autoCompleteUsoEQuantita(0);
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
                //if(!autoComp.getText().toString().isEmpty()&&!descPatologia.getText().toString().isEmpty()){
                Invia();
                //}
            }
        });

    }

    private void autoCompleteUsoEQuantita(int pos){

        if(pos == 0){
            set = true;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, c.farmaci.get(autoComp.getText().toString()).getQuatitaEuso());
            dropdown.setAdapter(adapter);
            String supp  = "PREZZO: <b>" + c.farmaci.get(autoComp.getText().toString()).getPrezzo().get(dropdown.getSelectedItemPosition()) + "€</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else if(pos == -1) {
            String supp  = "PREZZO: <b>" + c.farmaci.get(autoComp.getText().toString()).getPrezzo().get(dropdown.getSelectedItemPosition()) + "€</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else {
            set = true;
            String[] nullo = {"QUANTITÀ e UTILIZZO"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nullo);
            dropdown.setAdapter(adapter);
            prezzoFarmaco.setText("PREZZO:");
        }
    }


    private void Invia() {

        sql = "INSERT INTO Ricetta (IdMedico, IdPaziente, IdFarmaco, NumeroScatole, Descrizione, EsenzionePatologia, EsenzioneReddito, StatoRichiesta, Data, Ora) " +
                "VALUES (1, 1, 1, 2, 'Sto morendo', 1, 0, 'IN ATTESA', '2019-03-12', '16:42');";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sql", sql);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        finish();

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
