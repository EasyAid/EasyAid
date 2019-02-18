package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.easy.aid.R;

/**
 *
 * @since 1.0 07/02/2019
 * @version 1.0
 *
 * @author Tironi
 *
 */

public class AccessoPaziente extends AppCompatActivity {

    private EditText user, pwd;
    private Button accedi, registrazione;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_accesso);


        user            = (EditText) findViewById(R.id.accessoCodiceFiscalePaz);
        pwd             = (EditText) findViewById(R.id.accessoPasswordPaz);
        accedi          = (Button)   findViewById(R.id.accessoButtonPaz);
        registrazione   = (Button)   findViewById(R.id.registrazioneButtonPaz);
        back            = (ImageView)findViewById(R.id.backAccessoPaz);


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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //TODO CONTROLLARE SE USER E PWD CORRISPONDONO NEL DB
    private void accediFun(){
        Intent i = new Intent(AccessoPaziente.this, MainPaziente.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
