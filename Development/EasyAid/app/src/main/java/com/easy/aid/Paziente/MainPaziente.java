package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.easy.aid.R;

/**
 * @author Cristian
 *
 */

public class MainPaziente extends AppCompatActivity {

    private LinearLayout richiediVisRic, ordinaRicetta, impostazioni, cronologia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_paziente);

        richiediVisRic = (LinearLayout) findViewById(R.id.richiediVisitaRicettaPaz);
        ordinaRicetta = (LinearLayout) findViewById(R.id.ordinaRicettaPaz);
        impostazioni = (LinearLayout) findViewById(R.id.impostazioniPaz);
        cronologia = (LinearLayout) findViewById(R.id.cronologiaPaz);

        richiediVisRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, RichiediVisitaRicettaPaz.class);
                startActivity(i);
            }
        });

        ordinaRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, OrdinaRicettaPaz.class);
                startActivity(i);
            }
        });

        impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, ImpostazioniPaz.class);
                startActivity(i);
            }
        });

        cronologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPaziente.this, CronologiaPaz.class);
                startActivity(i);
            }
        });

    }
}
