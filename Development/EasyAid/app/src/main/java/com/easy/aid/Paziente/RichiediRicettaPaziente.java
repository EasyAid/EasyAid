package com.easy.aid.Paziente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.easy.aid.R;

public class RichiediRicettaPaziente extends AppCompatActivity {

    private TextView nomeCognomePaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_ricetta);

        nomeCognomePaz = (TextView) findViewById(R.id.nomeCognomeRichiediRicetta);

    }
}
