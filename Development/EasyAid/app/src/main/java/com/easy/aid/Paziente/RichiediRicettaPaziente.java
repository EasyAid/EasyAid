package com.easy.aid.Paziente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.easy.aid.Class.Paziente;
import com.easy.aid.R;

import java.io.File;
import java.io.FileInputStream;

public class RichiediRicettaPaziente extends AppCompatActivity {

    private TextView nomeCognomePaz, nomeCognomeMed;
    private FileInputStream fileFarmaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_ricetta);

        nomeCognomePaz = (TextView) findViewById(R.id.nomeCognomePazienteRichiediRicettaPaz);
        nomeCognomeMed = (TextView) findViewById(R.id.nomeCognomeMedicoRichiediRicettaPaz);


    }
}
