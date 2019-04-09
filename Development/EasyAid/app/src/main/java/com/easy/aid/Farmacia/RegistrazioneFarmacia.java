package com.easy.aid.Farmacia;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.easy.aid.Class.TimePickerFragment;
import com.easy.aid.R;

public class RegistrazioneFarmacia extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private String txtContinuaReg = "CONTINUA";
    private String txtTerminaReg = "CONFERMA REGISTRAZIONE";
    private int status;
    private Intent intent;
    private Button continuaReg;
    private ImageView back;
    private LinearLayout Reg1, Reg2;
    private TextView[] settimanaMattina, settimanaSera;
    private CheckBox[] lavoro, pausa;

    //elementi pagina1
    private EditText mail, telefono, psw, confPsw, provincia, citta, via;


    private int idGiorno, count, indice;
    private boolean mattina;

    //elementi pagina2
    //TODO aggiungere qualcosa del calendario quando pronto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_registrazione);

        status = 0;
        continuaReg = (Button) findViewById(R.id.continuaRegistrazioneFarmacia);
        back = (ImageView) findViewById(R.id.backRegistrazioneFarmacia);

        Reg1 = (LinearLayout) findViewById(R.id.registrazione1Farmacia);
        Reg2 = (LinearLayout) findViewById(R.id.registrazione2Farmacia);

        //elementi pagina1
        mail = (EditText) findViewById(R.id.editEmailFarmacia);
        telefono = (EditText) findViewById(R.id.editTelFarmacia);
        psw = (EditText) findViewById(R.id.editPswFarmacia);
        confPsw = (EditText) findViewById(R.id.confPswFarmacia);
        provincia = (EditText) findViewById(R.id.editProvinciaFarmacia);
        citta = (EditText) findViewById(R.id.editCittaFarmacia);
        via = (EditText) findViewById(R.id.editViaFarmacia);

        //elementi pagina2
        // check lavoro
        lavoro = new CheckBox[7];
        lavoro[0] = (CheckBox) findViewById(R.id.lavoroLunFarmacia);
        lavoro[0].setOnClickListener(this);
        lavoro[1] = (CheckBox) findViewById(R.id.lavoroMarFarmacia);
        lavoro[1].setOnClickListener(this);
        lavoro[2] = (CheckBox) findViewById(R.id.lavoroMerFarmacia);
        lavoro[2].setOnClickListener(this);
        lavoro[3] = (CheckBox) findViewById(R.id.lavoroGioFarmacia);
        lavoro[3].setOnClickListener(this);
        lavoro[4] = (CheckBox) findViewById(R.id.lavoroVenFarmacia);
        lavoro[4].setOnClickListener(this);
        lavoro[5] = (CheckBox) findViewById(R.id.lavoroSabFarmacia);
        lavoro[5].setOnClickListener(this);
        lavoro[6] = (CheckBox) findViewById(R.id.lavoroDomFarmacia);
        lavoro[6].setOnClickListener(this);

        // orari primo turno
        settimanaMattina = new TextView[7];
        settimanaMattina[0] = (TextView) findViewById(R.id.mattinaLunFarmacia);
        settimanaMattina[0].setOnClickListener(this);
        settimanaMattina[1] = (TextView) findViewById(R.id.mattinaMarFarmacia);
        settimanaMattina[1].setOnClickListener(this);
        settimanaMattina[2] = (TextView) findViewById(R.id.mattinaMerFarmacia);
        settimanaMattina[2].setOnClickListener(this);
        settimanaMattina[3] = (TextView) findViewById(R.id.mattinaGioFarmacia);
        settimanaMattina[3].setOnClickListener(this);
        settimanaMattina[4] = (TextView) findViewById(R.id.mattinaVenFarmacia);
        settimanaMattina[4].setOnClickListener(this);
        settimanaMattina[5] = (TextView) findViewById(R.id.mattinaSabFarmacia);
        settimanaMattina[5].setOnClickListener(this);
        settimanaMattina[6] = (TextView) findViewById(R.id.mattinaDomFarmacia);
        settimanaMattina[6].setOnClickListener(this);

        // check pausa
        pausa = new CheckBox[7];   //pausaGiornoFarmacia
        pausa[0] = (CheckBox) findViewById(R.id.pausaLunFarmacia);
        pausa[0].setOnClickListener(this);
        pausa[1] = (CheckBox) findViewById(R.id.pausaMarFarmacia);
        pausa[1].setOnClickListener(this);
        pausa[2] = (CheckBox) findViewById(R.id.pausaMerFarmacia);
        pausa[2].setOnClickListener(this);
        pausa[3] = (CheckBox) findViewById(R.id.pausaGioFarmacia);
        pausa[3].setOnClickListener(this);
        pausa[4] = (CheckBox) findViewById(R.id.pausaVenFarmacia);
        pausa[4].setOnClickListener(this);
        pausa[5] = (CheckBox) findViewById(R.id.pausaSabFarmacia);
        pausa[5].setOnClickListener(this);
        pausa[6] = (CheckBox) findViewById(R.id.pausaDomFarmacia);
        pausa[6].setOnClickListener(this);

        // orari secondo turno
        settimanaSera = new TextView[7];
        settimanaSera[0] = (TextView) findViewById(R.id.seraLunFarmacia);
        settimanaSera[0].setOnClickListener(this);
        settimanaSera[1] = (TextView) findViewById(R.id.seraMarFarmacia);
        settimanaSera[1].setOnClickListener(this);
        settimanaSera[2] = (TextView) findViewById(R.id.seraMerFarmacia);
        settimanaSera[2].setOnClickListener(this);
        settimanaSera[3] = (TextView) findViewById(R.id.seraGioFarmacia);
        settimanaSera[3].setOnClickListener(this);
        settimanaSera[4] = (TextView) findViewById(R.id.seraVenFarmacia);
        settimanaSera[4].setOnClickListener(this);
        settimanaSera[5] = (TextView) findViewById(R.id.seraSabFarmacia);
        settimanaSera[5].setOnClickListener(this);
        settimanaSera[6] = (TextView) findViewById(R.id.seraDomFarmacia);
        settimanaSera[6].setOnClickListener(this);

        /* passaggio a seconda pagina di registrazione */
        continuaReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (status) {
                    case 0: /* prima pagina, bottone ha "continua" */ {
                        boolean error = false;
                        if (mail.getText().toString().isEmpty()) {
                            mail.setError("Inserisci mail");
                            error = true;
                        }
                        if (telefono.getText().toString().isEmpty()) {
                            telefono.setError("Inserisci recapito telefonico");
                            error = true;
                        }
                        if (psw.getText().toString().isEmpty()) {
                            psw.setError("Inserisci password");
                            error = true;
                        }
                        if (!confPsw.getText().toString().equals(psw.getText().toString())) {
                            confPsw.setError("Le password non coincidono");
                            error = true;
                        } else {
                            if (confPsw.getText().toString().isEmpty()) {
                                confPsw.setError("Inserisci password");
                                error = true;
                            }
                        }
                        if (provincia.getText().toString().isEmpty()) {
                            provincia.setError("Inserisci provincia");
                            error = true;
                        }
                        if (citta.getText().toString().isEmpty()) {
                            citta.setError("Inserisci citta");
                            error = true;
                        }
                        if (via.getText().toString().isEmpty()) {
                            via.setError("Inserisci via");
                            error = true;
                        }
                        if (!error) {
                            Reg1.setVisibility(View.GONE);
                            Reg2.setVisibility(View.VISIBLE);
                            continuaReg.setText(txtTerminaReg);
                            status = 1;
                        }
                    }
                    break;
                    case 1: /* pagina 2, bottone ha "Registrati" */ {
                        /* registrazione dell'utente */
                    }
                    break;
                }
            }
        });

        /* tornare a pagina di accesso */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mattinaLunFarmacia:
                orologio(0, true);
                break;
            case R.id.mattinaMarFarmacia:
                orologio(1, true);
                break;
            case R.id.mattinaMerFarmacia:
                orologio(2, true);
                break;
            case R.id.mattinaGioFarmacia:
                orologio(3, true);
                break;
            case R.id.mattinaVenFarmacia:
                orologio(4, true);
                break;
            case R.id.mattinaSabFarmacia:
                orologio(5, true);
                break;
            case R.id.mattinaDomFarmacia:
                orologio(6, true);
                break;
            case R.id.seraLunFarmacia:
                orologio(0, false);
                break;
            case R.id.seraMarFarmacia:
                orologio(1, false);
                break;
            case R.id.seraMerFarmacia:
                orologio(2, false);
                break;
            case R.id.seraGioFarmacia:
                orologio(3, false);
                break;
            case R.id.seraVenFarmacia:
                orologio(4, false);
                break;
            case R.id.seraSabFarmacia:
                orologio(5, false);
                break;
            case R.id.seraDomFarmacia:
                orologio(6, false);
                break;

                /////////////////////////////////////////////////////////////////////////////////////////////////////////

            case R.id.lavoroLunFarmacia:
                if (lavoro[0].isChecked()) {
                    enable(0,true);
                } else {
                    disable(0,true);
                }
                break;
            case R.id.lavoroMarFarmacia:
                if (lavoro[1].isChecked()) {
                    enable(1,true);
                } else {
                    disable(1,true);
                }
                break;
            case R.id.lavoroMerFarmacia:
                if (lavoro[2].isChecked()) {
                    enable(2,true);
                } else {
                    disable(2,true);
                }
                break;
            case R.id.lavoroGioFarmacia:
                if (lavoro[3].isChecked()) {
                    enable(3,true);
                } else {
                    disable(3,true);
                }
                break;
            case R.id.lavoroVenFarmacia:
                if (lavoro[4].isChecked()) {
                    enable(4,true);
                } else {
                    disable(4,true);
                }
                break;
            case R.id.lavoroSabFarmacia:
                if (lavoro[5].isChecked()) {
                    enable(5,true);
                } else {
                    disable(5,true);
                }
                break;
            case R.id.lavoroDomFarmacia:
                if (lavoro[6].isChecked()) {
                    enable(6,true);
                } else {
                    disable(6,true);
                }
                break;
            case R.id.pausaLunFarmacia:
                if (pausa[0].isChecked()) {
                    enable(0,false);
                } else {
                    disable(0,false);
                }
                break;
            case R.id.pausaMarFarmacia:
                if (pausa[1].isChecked()) {
                    enable(1,false);
                } else {
                    disable(1,false);
                }
                break;
            case R.id.pausaMerFarmacia:
                if (pausa[2].isChecked()) {
                    enable(2,false);
                } else {
                    disable(2,false);
                }
                break;
            case R.id.pausaGioFarmacia:
                if (pausa[3].isChecked()) {
                    enable(3,false);
                } else {
                    disable(3,false);
                }
                break;
            case R.id.pausaVenFarmacia:
                if (pausa[4].isChecked()) {
                    enable(4,false);
                } else {
                    disable(4,false);
                }
                break;
            case R.id.pausaSabFarmacia:
                if (pausa[5].isChecked()) {
                    enable(5,false);
                } else {
                    disable(5, false);
                }
                break;
            case R.id.pausaDomFarmacia:
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

    private void enable(int i, boolean work){

        if(work){
            settimanaMattina[i].setEnabled(true);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.greenDark));
        }
        pausa[i].setChecked(true);
        settimanaSera[i].setEnabled(true);
        settimanaSera[i].setTextColor(getResources().getColor(R.color.greenDark));
    }

    private void disable(int i,boolean work){

        if(work){
            settimanaMattina[i].setEnabled(false);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.def));
        }
        pausa[i].setChecked(false);
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
            String prevText = settimanaMattina[idGiorno].getText().toString();
            if (mattina)
                settimanaMattina[idGiorno].setText(prevText + "\n" + hourOfDay + ":" + minute);
            else settimanaSera[idGiorno].setText(prevText + "\n" + hourOfDay + ":" + minute);
        }
    }
}
