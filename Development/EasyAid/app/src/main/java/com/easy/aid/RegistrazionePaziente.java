package com.easy.aid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class RegistrazionePaziente extends AppCompatActivity {

    private EditText nome, cognome, dataNascita, provinciaNascita, cittaNascita, viaNascita;
    private EditText codiceFiscale, provinciaResidenza, cittaResidenza, viaResidenza;
    private EditText medicoBase, password, confermaPassword;

    private Button continua1, continua2, registrazione;

    private CalendarView calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_paziente);


        //TODO INSERIRE TUTTI I DATI ALL'INTERNO DI PAZIENTE SOLAMENTE DOPO CHE L'UTENTE HA CLICCATO REGISTRAZIONE
        Paziente paziente = new Paziente();

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
        });

    }
}
