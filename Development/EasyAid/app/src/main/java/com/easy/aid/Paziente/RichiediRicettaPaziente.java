package com.easy.aid.Paziente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Paziente;
import com.easy.aid.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**

 @author Cristian

 */

public class RichiediRicettaPaziente extends AppCompatActivity {

    private TextView nomeCognomePaz, nomeCognomeMed, usoFarmaco, prezzoFarmaco;
    private AutoCompleteTextView autoComp;
    private NetVariables c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_ricetta);

        c  = ((NetVariables) this.getApplication());

        nomeCognomePaz = (TextView) findViewById(R.id.nomeCognomePazienteRichiediRicettaPaz);
        nomeCognomeMed = (TextView) findViewById(R.id.nomeCognomeMedicoRichiediRicettaPaz);
        usoFarmaco = (TextView) findViewById(R.id.usoFarmacoRichiediRicettaPaz);
        prezzoFarmaco = (TextView) findViewById(R.id.prezzoFarmacoRichiediRicettaPaz);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, c.nomeFarmaci);

        autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteNomeFarmacoRichiediRicettaPaz);
        autoComp.setThreshold(1);//will start working from first character
        autoComp.setAdapter(adapter);

        autoComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteUsoEQuantita(position);
            }
        });

    }

    private void autoCompleteUsoEQuantita(int position){

        //TODO CONTROLLA CORRETTA POSIZIONE | AGGIUNGERE DROP DOWN MENU NEL CASO IN CUI NOMI UGUALI E QUANTITA DIVERSE

        if(position>=0){
            String supp = "QUANTITÀ e USO:<br><b>" + c.usoFarmaci.get(position) + "</b>";
            usoFarmaco.setText(Html.fromHtml(supp));
            supp = "PREZZO: <b>" + String.valueOf(c.prezzoFarmaci.get(position))+ "€" + "</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else if(position==-2){
            String supp = "QUANTITÀ e USO:<br>SCONOSCIUTO";
            usoFarmaco.setText(Html.fromHtml(supp));
            supp = "PREZZO: <b>SCONOSCIUTO</b>";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }else{
            String supp = "QUANTITÀ e USO:<br>";
            usoFarmaco.setText(Html.fromHtml(supp));
            supp = "PREZZO: ";
            prezzoFarmaco.setText(Html.fromHtml(supp));
        }
    }
}
