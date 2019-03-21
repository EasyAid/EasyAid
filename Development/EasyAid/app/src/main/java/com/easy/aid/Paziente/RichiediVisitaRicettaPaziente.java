package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easy.aid.Class.Paziente;
import com.easy.aid.R;

/**
 * @author Cristian
 *
 */

public class RichiediVisitaRicettaPaziente extends AppCompatActivity {

    private LinearLayout richiediRicetta, richiediVisita, visualizzaRichieste;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_visita_ricetta);


        //BUTTON per cambiare pagina
        richiediRicetta = (LinearLayout) findViewById(R.id.richiediRicetta);
        richiediVisita = (LinearLayout) findViewById(R.id.richiediVisita);
        visualizzaRichieste = (LinearLayout) findViewById(R.id.visualizzaRichieste);
        back = (ImageView) findViewById(R.id.backRichiediVisRicPaz);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
