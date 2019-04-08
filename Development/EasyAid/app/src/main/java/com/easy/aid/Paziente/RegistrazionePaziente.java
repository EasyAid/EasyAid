package com.easy.aid.Paziente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;

import com.easy.aid.R;

public class RegistrazionePaziente extends AppCompatActivity {

    private EditText nome, cognome, dataNascita, provinciaNascita, cittaNascita, viaNascita;
    private EditText codiceFiscale, provinciaResidenza, cittaResidenza, viaResidenza;
    private EditText medicoBase, password, confermaPassword;
    private Button continua1, continua2, registrazione;
    private CalendarView calendario;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_registrazione);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        registrazione   = (Button) findViewById(R.id.continuaPaziente);

        nome = (EditText) findViewById(R.id.editNomePaziente);
        cognome = (EditText) findViewById(R.id.editCognomePaziente);
        dataNascita = (EditText) findViewById(R.id.editDataNascitaPaziente);
        provinciaNascita = (EditText) findViewById(R.id.editProvinciaNascPaziente);
        cittaNascita = (EditText) findViewById(R.id.editCittaNascPaziente);
        viaNascita = (EditText) findViewById(R.id.editViaNascPaziente);

        back = (ImageView)findViewById(R.id.backRegistrazionePaziente);

        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if(nome.getText().toString().isEmpty()){
                    nome.setError("Inserisci nome");
                    error = true;
                }
                if(cognome.getText().toString().isEmpty()){
                    cognome.setError("Inserisci cognome");
                    error = true;
                }
                if(dataNascita.getText().toString().isEmpty()){
                    dataNascita.setError("Inserisci data di nascita");
                    error = true;
                }
                if(provinciaNascita.getText().toString().isEmpty()){
                    provinciaNascita.setError("Inserisci provincia di nascita");
                    error = true;
                }
                if(cittaNascita.getText().toString().isEmpty()){
                    cittaNascita.setError("Inserisci città di nascita");
                    error = true;
                }
                if(viaNascita.getText().toString().isEmpty()){
                    viaNascita.setError("Inserisci via di nascita");
                    error = true;
                }
                if(!error){

                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //TODO INSERIRE TUTTI I DATI ALL'INTERNO DI PAZIENTE SOLAMENTE DOPO CHE L'UTENTE HA CLICCATO REGISTRAZIONE
        /*Paziente paziente = new Paziente();

        paziente.setNome((
                (EditText) findViewById(R.id.editNomePaz)).getText().toString());
        paziente.setCognome((
                (EditText) findViewById(R.id.editCognomePaz)).getText().toString());
        paziente.setDataNascita((
                (EditText) findViewById(R.id.editDataNascitaPaz)).getText().toString());
        paziente.setProvinciaNascita((
                (EditText) findViewById(R.id.editProvinciaNascPaz)).getText().toString());
        paziente.setCittaNascita((
                (EditText) findViewById(R.id.editCittaNascPaz)).getText().toString());
        paziente.setViaNascita((
                (EditText) findViewById(R.id.editViaNascPaz)).getText().toString());
        paziente.setProvinciaNascita((
                (EditText) findViewById(R.id.editProvinciaNascPaz)).getText().toString());
        paziente.setCittaNascita((
                (EditText) findViewById(R.id.editCittaNascPaz)).getText().toString());
        paziente.setViaNascita((
                (EditText) findViewById(R.id.editViaNascPaz)).getText().toString());
        paziente.setCodiceFiscale((
                (EditText) findViewById(R.id.editCodiceFiscalePaz)).getText().toString());
        paziente.setMedicoBase((
                (EditText) findViewById(R.id.editMedicoPaz)).getText().toString());
        paziente.setPassword((
                (EditText) findViewById(R.id.editPasswordPaz)).getText().toString());
        paziente.setConfermaPassword((
                (EditText) findViewById(R.id.editConfermaPasswordPaz)).getText().toString());

        calendario = (CalendarView) findViewById(R.id.calendario);

        dataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.setVisibility(View.VISIBLE);
            }
        });*/

    }
}
