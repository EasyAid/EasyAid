package com.easy.aid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @since 1.0 07/02/2019
 * @version 1.0
 *
 * @author Tironi 07/02/2019 51:58
 *
 */

public class AccessoPaziente extends AppCompatActivity {

    private EditText user, pwd;
    private Button accedi, registrazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accesso_paziente);


        user            = (EditText) findViewById(R.id.accessoCodiceFiscalePaz);
        pwd             = (EditText) findViewById(R.id.accessoPasswordPaz);
        accedi          = (Button)   findViewById(R.id.accessoButtonPaz);
        registrazione   = (Button)   findViewById(R.id.registrazioneButtonPaz);


        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accediFun();
            }
        });

        //APRE L' ACTIVITY REGISTRAZIONE PAZIENTE
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccessoPaziente.this, RegistrazionePaziente.class);
                startActivity(i);
            }
        });
    }

    //TODO CONTROLLARE SE USER E PWD CORRISPONDONO NEL DB
    private void accediFun(){

    }
}
