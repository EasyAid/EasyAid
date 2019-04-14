package com.easy.aid.Medico;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.easy.aid.Class.Calendario;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.Medico;
import com.easy.aid.Class.TimePickerFragment;
import com.easy.aid.R;

import org.w3c.dom.Text;

import java.sql.Time;

public class RegistrazioneMedico extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private String btnTextContinua = "CONTINUA";
    private String btnTextFinisci = "CONFERMA REGISTRAZIONE";

    private Calendario calendario; private Time temp;
    private ScrollView registrazione1, registrazione2, registrazione3;
    private Button continua1;
    private ImageView back;
    private int status;
    private Intent intent;

    private TextView[] settimanaMattina, settimanaSera;
    private CheckBox[] lavoro, pausa;

    private EditText nome;
    private EditText cognome;
    private EditText dataNascita;
    private RadioGroup sesso;
    private RadioButton maschio;
    private RadioButton femmina;
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

    private int idGiorno, count, indice;
    private boolean mattina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_registrazione);

        //CONTROLLA LE API DEL TELEFONO, SE MAGGIORI DI MARSHMELLOW MODIFICA IL COLORE DEL TESTO DELLA NOTIFICATION BAR IN CHIARO
        //ALTRIMENTI SE E' INFERIORE ALLE API 23 MODIFICA LA NOTIFICATION BAR IN COLORE SCURO (IN QUANTO NON PUO MODIFICARE IL COLORE DEL TESTO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(),R.color.colorAccent));
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //nasconde tastiera

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
        maschio = (RadioButton) findViewById(R.id.maschioMed);
        femmina = (RadioButton) findViewById(R.id.femminaMed);
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

        //lavoro
        lavoro = new CheckBox[7];
        lavoro[0] = (CheckBox)findViewById(R.id.lavoroLunMed);
        lavoro[0].setOnClickListener(this);
        lavoro[1] = (CheckBox)findViewById(R.id.lavoroMarMed);
        lavoro[1].setOnClickListener(this);
        lavoro[2] = (CheckBox)findViewById(R.id.lavoroMerMed);
        lavoro[2].setOnClickListener(this);
        lavoro[3] = (CheckBox)findViewById(R.id.lavoroGioMed);
        lavoro[3].setOnClickListener(this);
        lavoro[4] = (CheckBox)findViewById(R.id.lavoroVenMed);
        lavoro[4].setOnClickListener(this);
        lavoro[5] = (CheckBox)findViewById(R.id.lavoroSabMed);
        lavoro[5].setOnClickListener(this);
        lavoro[6] = (CheckBox)findViewById(R.id.lavoroDomMed);
        lavoro[6].setOnClickListener(this);

        //pausa
        pausa = new CheckBox[7];
        pausa[0] = (CheckBox) findViewById(R.id.pausaLunMed);
        pausa[0].setOnClickListener(this);
        pausa[1] = (CheckBox) findViewById(R.id.pausaMarMed);
        pausa[1].setOnClickListener(this);
        pausa[2] = (CheckBox) findViewById(R.id.pausaMerMed);
        pausa[2].setOnClickListener(this);
        pausa[3] = (CheckBox) findViewById(R.id.pausaGioMed);
        pausa[3].setOnClickListener(this);
        pausa[4] = (CheckBox) findViewById(R.id.pausaVenMed);
        pausa[4].setOnClickListener(this);
        pausa[5] = (CheckBox) findViewById(R.id.pausaSabMed);
        pausa[5].setOnClickListener(this);
        pausa[6] = (CheckBox) findViewById(R.id.pausaDomMed);
        pausa[6].setOnClickListener(this);

        //timepicker
        settimanaMattina = new TextView[7];
        settimanaMattina[0] = (TextView) findViewById(R.id.mattinaLunMedico);
        settimanaMattina[0].setOnClickListener(this);
        settimanaMattina[1] = (TextView) findViewById(R.id.mattinaMarMedico);
        settimanaMattina[1].setOnClickListener(this);
        settimanaMattina[2] = (TextView) findViewById(R.id.mattinaMerMedico);
        settimanaMattina[2].setOnClickListener(this);
        settimanaMattina[3] = (TextView) findViewById(R.id.mattinaGioMedico);
        settimanaMattina[3].setOnClickListener(this);
        settimanaMattina[4] = (TextView) findViewById(R.id.mattinaVenMedico);
        settimanaMattina[4].setOnClickListener(this);
        settimanaMattina[5] = (TextView) findViewById(R.id.mattinaSabMedico);
        settimanaMattina[5].setOnClickListener(this);
        settimanaMattina[6] = (TextView) findViewById(R.id.mattinaDomMedico);
        settimanaMattina[6].setOnClickListener(this);

        settimanaSera = new TextView[7];
        settimanaSera[0] = (TextView) findViewById(R.id.seraLunMedico);
        settimanaSera[0].setOnClickListener(this);
        settimanaSera[1] = (TextView) findViewById(R.id.seraMarMedico);
        settimanaSera[1].setOnClickListener(this);
        settimanaSera[2] = (TextView) findViewById(R.id.seraMerMedico);
        settimanaSera[2].setOnClickListener(this);
        settimanaSera[3] = (TextView) findViewById(R.id.seraGioMedico);
        settimanaSera[3].setOnClickListener(this);
        settimanaSera[4] = (TextView) findViewById(R.id.seraVenMedico);
        settimanaSera[4].setOnClickListener(this);
        settimanaSera[5] = (TextView) findViewById(R.id.seraSabMedico);
        settimanaSera[5].setOnClickListener(this);
        settimanaSera[6] = (TextView) findViewById(R.id.seraDomMedico);
        settimanaSera[6].setOnClickListener(this);

        continua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 0: {
                        registrazione1.setVisibility(View.GONE);
                        registrazione2.setVisibility(View.VISIBLE);
                        status = 1;
                        break;
                    }
                    case 1: {
                        registrazione2.setVisibility(View.GONE);
                        registrazione3.setVisibility(View.VISIBLE);
                        status = 2;
                        continua1.setText(btnTextFinisci);
                        break;
                    }
                    case 2: {
                        //controllo su tutti i campi
                        //page = 1 torna alla prima pagina
                        //page = 2 torna alla seconda pagina etc...
                        int page = 0;

                        //page = 3
                        if (email.getText().toString().isEmpty()) {
                            page = 3;
                            email.setError("inserire email");
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                            email.setError("email non valida");
                        }

                        if (telefono.getText().toString().isEmpty()) {
                            page = 3;
                            telefono.setError("inserire numero di telefono");
                        } else if (!Patterns.PHONE.matcher(telefono.getText().toString()).matches()) {
                            page = 3;
                            telefono.setError("numero di telefono errato");
                        }

                        if (password.getText().toString().isEmpty()) {
                            page = 3;
                            password.setError("inserire password");
                        }
                        if (password.getText().toString().length() > 20) {
                            page = 3;
                            password.setError("massimo 20 caratteri");
                        }

                        if (passwordConferma.getText().toString().isEmpty()) {
                            page = 3;
                            passwordConferma.setError("confermare la password");
                        } else if (!passwordConferma.getText().toString().equals(password.getText().toString())) {
                            page = 3;
                            passwordConferma.setError("le password non coincidono");
                        }


                        //page = 2
                        if (provinciaStudio.getText().toString().isEmpty()) {
                            page = 2;
                            provinciaStudio.setError("inserire provincia");
                        }

                        if (cittaStudio.getText().toString().isEmpty()) {
                            page = 2;
                            cittaStudio.setError("inserire città");
                        }

                        if (viaStudio.getText().toString().isEmpty()) {
                            page = 2;
                            viaStudio.setError("inserire via");
                        }


                        //page = 1

                        if (nome.getText().toString().isEmpty()) {
                            page = 1;
                            nome.setError("Inserisci nome");
                        }

                        if (cognome.getText().toString().isEmpty()) {
                            page = 1;
                            cognome.setError("Inserisci cognome");
                        }

                        if (dataNascita.getText().toString().isEmpty()) {
                            page = 1;
                            dataNascita.setError("Inserisci data di nascita");
                        }

                        if (sesso.getCheckedRadioButtonId() == -1) {
                            page = 1;
                            maschio.setError("selezionare");
                            femmina.setError("selezionare");
                        }

                        if (provincia.getText().toString().isEmpty()) {
                            page = 1;
                            provincia.setError("Inserisci provincia");
                        }

                        if (citta.getText().toString().isEmpty()) {
                            page = 1;
                            citta.setError("Inserisci città");
                        }

                        if (via.getText().toString().isEmpty()) {
                            page = 1;
                            via.setError("Inserisci via");
                        }

                        if (cf.getText().toString().isEmpty()) {
                            page = 1;
                            cf.setError("Inserisci codice fiscale");
                        }

                        switch (page) {
                            case 0:
                                //tutto giusto
                                boolean boolMaschio = true;
                                if (femmina.isChecked()) {
                                    boolMaschio = false;
                                }
                                Indirizzo luogoNascita = new Indirizzo(provincia.getText().toString(), citta.getText().toString(), via.getText().toString());
                                Indirizzo studio = new Indirizzo(provinciaStudio.getText().toString(), cittaStudio.getText().toString(), viaStudio.getText().toString());
                                Medico nuovoMed = new Medico(nome.getText().toString(), cognome.getText().toString(), dataNascita.getText().toString(), boolMaschio, cf.getText().toString(), luogoNascita, studio, password.getText().toString(), email.getText().toString(), telefono.getText().toString());
                                intent = new Intent(RegistrazioneMedico.this, MainMedico.class);
                                startActivity(intent);
                                // TODO: 08/04/2019 crea il medico nel database
                                finish();
                                break;

                            case 1:
                                //torna alla prima pagina di registrazione
                                continua1.setText(btnTextContinua);
                                status = 0;
                                registrazione3.setVisibility(View.GONE);
                                registrazione2.setVisibility(View.GONE);
                                registrazione1.setVisibility(View.VISIBLE);
                                break;

                            case 2:
                                //torna alla seconda pagina di registrazione
                                continua1.setText(btnTextContinua);
                                status = 1;
                                registrazione3.setVisibility(View.GONE);
                                registrazione2.setVisibility(View.VISIBLE);
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
                checkBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        checkBack();
    }

    private void checkBack(){
        switch (status) {
            case 0: {
                finish();
                break;
            }
            case 1: {
                status--;
                registrazione2.setVisibility(View.GONE);
                registrazione1.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                continua1.setText(btnTextContinua);
                status--;
                registrazione3.setVisibility(View.GONE);
                registrazione2.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //casi orari
            case R.id.mattinaLunMedico:
                orologio(0, true);
                break;
            case R.id.mattinaMarMedico:
                orologio(1, true);
                break;
            case R.id.mattinaMerMedico:
                orologio(2, true);
                break;
            case R.id.mattinaGioMedico:
                orologio(3, true);
                break;
            case R.id.mattinaVenMedico:
                orologio(4, true);
                break;
            case R.id.mattinaSabMedico:
                orologio(5, true);
                break;
            case R.id.mattinaDomMedico:
                orologio(6, true);
                break;
            case R.id.seraLunMedico:
                orologio(0, false);
                break;
            case R.id.seraMarMedico:
                orologio(1, false);
                break;
            case R.id.seraMerMedico:
                orologio(2, false);
                break;
            case R.id.seraGioMedico:
                orologio(3, false);
                break;
            case R.id.seraVenMedico:
                orologio(4, false);
                break;
            case R.id.seraSabMedico:
                orologio(5, false);
                break;
            case R.id.seraDomMedico:
                orologio(6, false);
                break;


            case R.id.lavoroLunMed:
                if (lavoro[0].isChecked()) {
                    enable(0,true);
                } else {
                    disable(0,true);
                }
                break;
            case R.id.lavoroMarMed:
                if (lavoro[1].isChecked()) {
                    enable(1,true);
                } else {
                    disable(1,true);
                }
                break;
            case R.id.lavoroMerMed:
                if (lavoro[2].isChecked()) {
                    enable(2,true);
                } else {
                    disable(2,true);
                }
                break;
            case R.id.lavoroGioMed:
                if (lavoro[3].isChecked()) {
                    enable(3,true);
                } else {
                    disable(3,true);
                }
                break;
            case R.id.lavoroVenMed:
                if (lavoro[4].isChecked()) {
                    enable(4,true);
                } else {
                    disable(4,true);
                }
                break;
            case R.id.lavoroSabMed:
                if (lavoro[5].isChecked()) {
                    enable(5,true);
                } else {
                    disable(5,true);
                }
                break;
            case R.id.lavoroDomMed:
                if (lavoro[6].isChecked()) {
                    enable(6,true);
                } else {
                    disable(6,true);
                }
                break;
            case R.id.pausaLunMed:
                if (pausa[0].isChecked()) {
                    enable(0,false);
                } else {
                    disable(0,false);
                }
                break;
            case R.id.pausaMarMed:
                if (pausa[1].isChecked()) {
                    enable(1,false);
                } else {
                    disable(1,false);
                }
                break;
            case R.id.pausaMerMed:
                if (pausa[2].isChecked()) {
                    enable(2,false);
                } else {
                    disable(2,false);
                }
                break;
            case R.id.pausaGioMed:
                if (pausa[3].isChecked()) {
                    enable(3,false);
                } else {
                    disable(3,false);
                }
                break;
            case R.id.pausaVenMed:
                if (pausa[4].isChecked()) {
                    enable(4,false);
                } else {
                    disable(4,false);
                }
                break;
            case R.id.pausaSabMed:
                if (pausa[5].isChecked()) {
                    enable(5,false);
                } else {
                    disable(5, false);
                }
                break;
            case R.id.pausaDomMed:
                if (pausa[6].isChecked()) {
                    enable(6,false);
                } else {
                    disable(6,false);
                }
                break;
        }
    }

    private void orologio(int id, boolean m) {
        idGiorno = id;
        mattina = m;
        count = 0;
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
        timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    private void enable(int i, boolean lavoro){

        if(lavoro){
            settimanaMattina[i].setEnabled(true);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.greenDark));
        }
        pausa[i].setChecked(true);
        settimanaSera[i].setEnabled(true);
        settimanaSera[i].setTextColor(getResources().getColor(R.color.greenDark));
    }

    private void disable(int i,boolean lavoro){

        if(lavoro){
            settimanaMattina[i].setEnabled(false);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.def));
            pausa[i].setChecked(false);
        }
        settimanaSera[i].setEnabled(false);
        settimanaSera[i].setTextColor(getResources().getColor(R.color.def));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (count == 0) {
            if (mattina) settimanaMattina[idGiorno].setText(hourOfDay + ":" + minute);
            else settimanaSera[idGiorno].setText(hourOfDay + ":" + minute);
            count = 1;
        } else {
            String prevText;

            if(mattina) { prevText = settimanaMattina[idGiorno].getText().toString(); }
            else { prevText = settimanaSera[idGiorno].getText().toString(); }

            String nextText = hourOfDay + ":" + minute;

            int[] temp = new int[2];
            temp[0] = Integer.parseInt(prevText.split(":")[0]);
            temp[1] = Integer.parseInt(prevText.split(":")[1]);

            if (temp[0] > hourOfDay || (temp[0] == hourOfDay && temp[1] > minute)) {
                String aux = prevText;
                prevText = nextText;
                nextText = aux;
            }

            if (mattina) settimanaMattina[idGiorno].setText(prevText + "\n" + nextText);
            else settimanaSera[idGiorno].setText(prevText + "\n" + nextText);
        }
    }
}
