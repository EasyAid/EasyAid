package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easy.aid.R;

/**
 * @author Cristian
 *
 */

public class RichiediVisitaRicettaPaziente extends AppCompatActivity {

    private LinearLayout richiediRicetta, richiediVisita, visualizzaRichieste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_visita_ricetta);

        //BUTTON per cambiare pagina
        richiediRicetta = (LinearLayout) findViewById(R.id.richiediRicetta);
        richiediVisita = (LinearLayout) findViewById(R.id.richiediVisita);
        visualizzaRichieste = (LinearLayout) findViewById(R.id.visualizzaRichieste);

        richiediRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RichiediVisitaRicettaPaziente.this, RichiediRicettaPaziente.class);
                startActivity(i);
                finish();
            }
        });

        richiediVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RichiediVisitaRicettaPaziente.this, RichiestaVisitaPaziente.class);
                startActivity(i);
                finish();
            }
        });

    }
}
