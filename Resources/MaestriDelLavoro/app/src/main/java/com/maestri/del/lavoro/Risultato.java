package com.maestri.del.lavoro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Risultato extends AppCompatActivity {

    private int[] soluzioni = new int[]{ 3, 1, 1, 3, 1, 2, 1, 3, 1, 2, 1, 2, 3, 2, 1, 3, 2, 3, 2, 3, 1, 2, 3, 2, 1, 3, 2, 3, 1, 2 };

    private PublicVariables publicVariables;

    private int corrette=0,errate=0,nulle=0;

    private TextView valori, punteggio, esitoPositivo, esitoNegativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultato);

        publicVariables = new PublicVariables();

        valori = (TextView) findViewById(R.id.valoriRisposte);
        punteggio = (TextView) findViewById(R.id.punteggio);
        esitoPositivo = (TextView) findViewById(R.id.esitoPositivo);
        esitoNegativo = (TextView) findViewById(R.id.esitoNegativo);

        for(int i=0;i<30;i++){
            if(publicVariables.risposte[i] == -1){
                nulle ++;
            }else if(publicVariables.risposte[i] != soluzioni[i]){
                errate ++;
            }else{
                corrette++;
            }
        }

        valori.setText((corrette + "\n\n" + errate + "\n\n" + nulle));

        if(corrette+(errate*-1)+(nulle*-1)<0){
            punteggio.setText("0");
        }else{
            punteggio.setText((corrette+(errate*-1)+(nulle*-1)));
        }

        if(corrette+(errate*-1)+(nulle*-1)>=20){
            esitoPositivo.setVisibility(View.VISIBLE);
        }else{
            esitoNegativo.setVisibility(View.VISIBLE);
        }

    }
}
