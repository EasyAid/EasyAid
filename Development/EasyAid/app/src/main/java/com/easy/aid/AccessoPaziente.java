package com.easy.aid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @since 1.0 07/02/2019
 * @version 1.1
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


        user            = (EditText) findViewById(R.id.editCodiceFiscalePaz);
        pwd             = (EditText) findViewById(R.id.editPasswordPaz);
        accedi          = (Button)   findViewById(R.id.accessoButtonPaz);
        registrazione   = (Button)   findViewById(R.id.registrazioneButtonPaz);
    }
}
