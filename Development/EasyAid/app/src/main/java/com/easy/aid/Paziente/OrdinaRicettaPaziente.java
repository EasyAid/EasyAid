package com.easy.aid.Paziente;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.aid.Class.AdapterOrdinaFarmaco;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.R;

import java.util.ArrayList;
import java.util.Set;

public class OrdinaRicettaPaziente extends AppCompatActivity {

    private AutoCompleteTextView autoComp;
    private NetVariables global;
    private Button piu, meno, aggiungi;
    private TextView numero;
    private LinearLayout scegliRicetta, linearAddFarmaco;


    private RecyclerView recyclerView;

    private AdapterOrdinaFarmaco adapterOrdinaFarmaco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_ordina_ricetta);

        global = (NetVariables) this.getApplication();

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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        autoComp = findViewById(R.id.autoCompleteNomeFarmacoOrdinaPaziente);
        piu = findViewById(R.id.addOrdinaFarmacoPaziente);
        meno = findViewById(R.id.minusOrdinaFarmacoPaziente);
        numero = findViewById(R.id.numeroOrdinaFarmacoPaziente);
        aggiungi = findViewById(R.id.aggiungiCarrelloOrdinaFarmacoPaziente);
        scegliRicetta = findViewById(R.id.scegliRicettaOrdinaFarmacoPaziente);
        linearAddFarmaco = findViewById(R.id.linearAddFarmacoOrdinaFarmacoPaziente);
        recyclerView = findViewById(R.id.recyclerViewOrdinaFarmaco);

        Set<String> keys = global.farmaci.keySet();
        String[] nomeFarmaci = keys.toArray(new String[keys.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, nomeFarmaci);
        autoComp.setThreshold(1);//will start working from first character
        autoComp.setAdapter(adapter);

        piu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(numero.getText().toString());
                numero.setText(String.valueOf(n+1));
            }
        });

        meno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(numero.getText().toString());
                if(n!=0){
                    numero.setText(String.valueOf(n-1));
                }
            }
        });
        
        scegliRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddFarmaco.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterOrdinaFarmaco = new AdapterOrdinaFarmaco(global.ricette, global.farmaciID);
        recyclerView.setAdapter(adapterOrdinaFarmaco);
    }
}
