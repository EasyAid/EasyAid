package com.easy.aid.Medico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.easy.aid.R;

public class RegistrazioneMedico extends AppCompatActivity {

    private ScrollView registrazione1, registrazione2, registrazione3;
    private Button continua1;
    private ImageView back;
    private int status;
    private Intent intent;

    private EditText nome;
    private EditText cognome;
    private EditText dataNascita;
    private RadioGroup sesso;
    private EditText provincia;
    private EditText citta;
    private EditText via;
    private EditText cf;
    private EditText provinciaStudio;
    private EditText cittaStudio;
    private EditText viaStudio;
    private EditText email;
    private EditText telefono;
    private EditText password;
    private EditText passwordConferma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_registrazione);

        back = (ImageView) findViewById(R.id.backRegistrazioneMed);
        registrazione1 = (ScrollView) findViewById(R.id.registrazione1Med);
        registrazione2 = (ScrollView) findViewById(R.id.registrazione2Med);
        registrazione3 = (ScrollView) findViewById(R.id.registrazione3Med);
        continua1 = (Button) findViewById(R.id.continuaMed);
        status = 0;

        //elementi della prima pagina
        nome = (EditText) findViewById(R.id.editNomeMed);
        cognome = (EditText) findViewById(R.id.editCognomeMed);
        dataNascita = (EditText) findViewById(R.id.editDataNascitaMed);
        sesso = (RadioGroup) findViewById(R.id.editSessoMed);
        provincia = (EditText) findViewById(R.id.editProvinciaNascMed);
        citta = (EditText) findViewById(R.id.editCittaNascMed);
        via = (EditText) findViewById(R.id.editViaNascMed);
        cf = (EditText) findViewById(R.id.editCodiceFiscaleMed);
        //elementi della seconda pagina
        provinciaStudio = (EditText) findViewById(R.id.editProvinciaStudMed);
        cittaStudio = (EditText) findViewById(R.id.editCittaStudMed);
        viaStudio = (EditText) findViewById(R.id.editViaStudMed);
        //elementi della terza pagina
        email = (EditText) findViewById(R.id.editEmailMed);
        telefono = (EditText) findViewById(R.id.editTelMed);
        password = (EditText) findViewById(R.id.editPasswordMed);
        passwordConferma = (EditText) findViewById(R.id.editConfermaPasswordMed);


        continua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 0: {
                        registrazione1.setVisibility(View.GONE);
                        registrazione2.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        registrazione2.setVisibility(View.GONE);
                        registrazione3.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        //controllo su tutti i campi
                        //page = 1 torna alla prima pagina
                        //page = 2 torna alla seconda pagina etc...
                        int page = 0;

                        //page = 3
                        if(email.getText().toString().isEmpty()){
                            page = 3;
                            email.setError("inserire email");
                        }
                        if(telefono.getText().toString().isEmpty()){
                            page = 3;
                            telefono.setError("inserire numero di telefono");
                        }
                        else if(telefono.getText().toString().length() != 10 && telefono.getText().toString().length() != 11){
                            page=3;
                            telefono.setError("numero di telefono errato");
                        }

                        if(password.getText().toString().isEmpty()){
                            page = 3;
                            password.setError("inserire password");
                        }

                        if(passwordConferma.getText().toString().isEmpty()){
                            page = 3;
                            passwordConferma.setError("confermare la password");
                        }
                        else if(!passwordConferma.getText().toString().equals(password.getText().toString())){
                            page = 3;
                            passwordConferma.setError("le password non coincidono");
                        }


                        //page = 2
                        if(provinciaStudio.getText().toString().isEmpty()){
                            page = 2;
                            provinciaStudio.setError("inserire provincia");
                        }

                        if(cittaStudio.getText().toString().isEmpty()){
                            page = 2;
                            cittaStudio.setError("inserire città");
                        }

                        if(viaStudio.getText().toString().isEmpty()){
                            page = 2;
                            viaStudio.setError("inserire via");
                        }

                        // TODO: 08/04/2019 controllo calendario


                        //page = 1

                        if(nome.getText().toString().isEmpty()){
                            page = 1;
                            nome.setError("Inserisci nome");
                        }

                        if(cognome.getText().toString().isEmpty()){
                            page = 1;
                            cognome.setError("Inserisci cognome");
                        }

                        if(dataNascita.getText().toString().isEmpty()){
                            page = 1;
                            dataNascita.setError("Inserisci data di nascita");
                        }
                        // TODO: 08/04/2019 controllo sul sesso


                        if(provincia.getText().toString().isEmpty()){
                            page = 1;
                            provincia.setError("Inserisci provincia");
                        }

                        if(citta.getText().toString().isEmpty()){
                            page = 1;
                            citta.setError("Inserisci città");
                        }

                        if(via.getText().toString().isEmpty()){
                            page = 1;
                            via.setError("Inserisci via");
                        }

                        if(cf.getText().toString().isEmpty()){
                            page = 1;
                            cf.setError("Inserisci codice fiscale");
                        }

                        switch (page) {
                            case 0:
                                //tutto giusto
                                intent = new Intent(RegistrazioneMedico.this, MainMedico.class);
                                startActivity(intent);
                                // TODO: 08/04/2019 crea il medico nel database
                                finish();
                                break;

                            case 1:
                                //torna alla prima pagina di registrazione
                                registrazione3.setVisibility(View.GONE);
                                registrazione2.setVisibility(View.GONE);
                                registrazione1.setVisibility(View.VISIBLE);
                                break;

                            case 2:
                                //torna alla seconda pagina di registrazione
                                registrazione3.setVisibility(View.GONE);
                                registrazione2.setVisibility(View.VISIBLE);
                                registrazione1.setVisibility(View.GONE);
                                break;

                            case 3:
                                //torna alla terza pagina di registrazione
                                registrazione3.setVisibility(View.VISIBLE);
                                registrazione2.setVisibility(View.GONE);
                                registrazione1.setVisibility(View.GONE);
                                break;
                        }

                    }
                }

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 0: {
                        finish();
                        break;
                    }
                    case 1: {
                        registrazione2.setVisibility(View.GONE);
                        registrazione1.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        registrazione3.setVisibility(View.GONE);
                        registrazione2.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }
}
