package com.easy.aid.Medico;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.Calendario;
import com.easy.aid.Class.Indirizzo;
import com.easy.aid.Class.Medico;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.TimePickerFragment;
import com.easy.aid.Farmacia.MainFarmacia;
import com.easy.aid.Farmacia.RegistrazioneFarmacia;
import com.easy.aid.InitialSplashScreen;
import com.easy.aid.Paziente.AccessoPaziente;
import com.easy.aid.Paziente.MainPaziente;
import com.easy.aid.Paziente.RegistrazionePaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.easy.aid.Class.Calendario.stringToTime;

public class RegistrazioneMedico extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private String btnTextContinua = "CONTINUA";
    private String btnTextFinisci = "CONFERMA REGISTRAZIONE";
    private NetVariables global;

    private Calendario calendario;
    private Time temp;
    private ScrollView registrazione1, registrazione2, registrazione3;
    private Button continua1;
    private ImageView back;
    private int status;
    private Intent intent;

    private TextView[] settimanaMattina, settimanaSera;
    private CheckBox[] turno1, turno2;

    private EditText nome;
    private EditText cognome;
    private EditText dataNascita;
    private RadioGroup sesso;
    private RadioButton maschio;
    private RadioButton femmina;
    private AutoCompleteTextView provincia;
    private AutoCompleteTextView citta;
    private EditText cf;
    private AutoCompleteTextView provinciaStudio;
    private AutoCompleteTextView cittaStudio;
    private EditText viaStudio;
    private EditText email;
    private EditText telefono;
    private EditText password;
    private EditText passwordConferma;

    private List<String> comuniRistretti;
    private int idGiorno, count, indice;
    private boolean mattina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_registrazione);

        global = (NetVariables) this.getApplication();


        checkAPI();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //nasconde tastiera

        back = findViewById(R.id.backRegistrazioneMed);
        registrazione1 = findViewById(R.id.registrazione1Med);
        registrazione2 = findViewById(R.id.registrazione2Med);
        registrazione3 = findViewById(R.id.registrazione3Med);
        continua1 = findViewById(R.id.continuaMed);
        status = 0;

        //elementi della prima pagina
        nome = findViewById(R.id.editNomeMed);
        cognome = findViewById(R.id.editCognomeMed);
        dataNascita = findViewById(R.id.editDataNascitaMed);
        sesso = findViewById(R.id.editSessoMed);
        maschio = findViewById(R.id.maschioMed);
        femmina = findViewById(R.id.femminaMed);
        provincia = findViewById(R.id.editProvinciaNascMed);
        citta = findViewById(R.id.editCittaNascMed);
        cf = findViewById(R.id.editCodiceFiscaleMed);
        //elementi della seconda pagina
        provinciaStudio = findViewById(R.id.editProvinciaStudMed);
        cittaStudio = findViewById(R.id.editCittaStudMed);
        viaStudio = findViewById(R.id.editViaStudMed);
        //elementi della terza pagina
        email = findViewById(R.id.editEmailMed);
        telefono = findViewById(R.id.editTelMed);
        password = findViewById(R.id.editPasswordMed);
        passwordConferma = findViewById(R.id.editConfermaPasswordMed);

        comuniRistretti = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, global.province);

        provincia.setThreshold(1);//will start working from first character
        provincia.setAdapter(adapter);
        provinciaStudio.setThreshold(1);//will start working from first character
        provinciaStudio.setAdapter(adapter);

        provincia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                comuniRistretti.clear();

                int pos = global.province.indexOf(provincia.getText().toString());
                String sigla = global.siglaProvince.get(pos);
                for(int i=0;i<global.siglaProvinceComuni.size();i++){
                    if(sigla.equals(global.siglaProvinceComuni.get(i))){
                        comuniRistretti.add(global.comuni.get(i));
                    }
                }

                nasc();
            }
        });

        provinciaStudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                comuniRistretti.clear();

                int pos = global.province.indexOf(provinciaStudio.getText().toString());
                String sigla = global.siglaProvince.get(pos);
                for(int i=0;i<global.siglaProvinceComuni.size();i++){
                    if(sigla.equals(global.siglaProvinceComuni.get(i))){
                        comuniRistretti.add(global.comuni.get(i));
                    }
                }

                res();

            }
        });


        citta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calcoloCodiceFiscale();
            }
        });

        //lavoro
        turno1 = new CheckBox[7];
        turno1[0] = findViewById(R.id.lavoroLunMed);
        turno1[0].setOnClickListener(this);
        turno1[1] = findViewById(R.id.lavoroMarMed);
        turno1[1].setOnClickListener(this);
        turno1[2] = findViewById(R.id.lavoroMerMed);
        turno1[2].setOnClickListener(this);
        turno1[3] = findViewById(R.id.lavoroGioMed);
        turno1[3].setOnClickListener(this);
        turno1[4] = findViewById(R.id.lavoroVenMed);
        turno1[4].setOnClickListener(this);
        turno1[5] = findViewById(R.id.lavoroSabMed);
        turno1[5].setOnClickListener(this);
        turno1[6] = findViewById(R.id.lavoroDomMed);
        turno1[6].setOnClickListener(this);

        //pausa
        turno2 = new CheckBox[7];
        turno2[0] = findViewById(R.id.pausaLunMed);
        turno2[0].setOnClickListener(this);
        turno2[1] = findViewById(R.id.pausaMarMed);
        turno2[1].setOnClickListener(this);
        turno2[2] = findViewById(R.id.pausaMerMed);
        turno2[2].setOnClickListener(this);
        turno2[3] = findViewById(R.id.pausaGioMed);
        turno2[3].setOnClickListener(this);
        turno2[4] = findViewById(R.id.pausaVenMed);
        turno2[4].setOnClickListener(this);
        turno2[5] = findViewById(R.id.pausaSabMed);
        turno2[5].setOnClickListener(this);
        turno2[6] = findViewById(R.id.pausaDomMed);
        turno2[6].setOnClickListener(this);

        //timepicker
        settimanaMattina = new TextView[7];
        settimanaMattina[0] = findViewById(R.id.mattinaLunMedico);
        settimanaMattina[0].setOnClickListener(this);
        settimanaMattina[1] = findViewById(R.id.mattinaMarMedico);
        settimanaMattina[1].setOnClickListener(this);
        settimanaMattina[2] = findViewById(R.id.mattinaMerMedico);
        settimanaMattina[2].setOnClickListener(this);
        settimanaMattina[3] = findViewById(R.id.mattinaGioMedico);
        settimanaMattina[3].setOnClickListener(this);
        settimanaMattina[4] = findViewById(R.id.mattinaVenMedico);
        settimanaMattina[4].setOnClickListener(this);
        settimanaMattina[5] = findViewById(R.id.mattinaSabMedico);
        settimanaMattina[5].setOnClickListener(this);
        settimanaMattina[6] = findViewById(R.id.mattinaDomMedico);
        settimanaMattina[6].setOnClickListener(this);

        settimanaSera = new TextView[7];
        settimanaSera[0] = findViewById(R.id.seraLunMedico);
        settimanaSera[0].setOnClickListener(this);
        settimanaSera[1] = findViewById(R.id.seraMarMedico);
        settimanaSera[1].setOnClickListener(this);
        settimanaSera[2] = findViewById(R.id.seraMerMedico);
        settimanaSera[2].setOnClickListener(this);
        settimanaSera[3] = findViewById(R.id.seraGioMedico);
        settimanaSera[3].setOnClickListener(this);
        settimanaSera[4] = findViewById(R.id.seraVenMedico);
        settimanaSera[4].setOnClickListener(this);
        settimanaSera[5] = findViewById(R.id.seraSabMedico);
        settimanaSera[5].setOnClickListener(this);
        settimanaSera[6] = findViewById(R.id.seraDomMedico);
        settimanaSera[6].setOnClickListener(this);

        cf.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    continua();
                }
                return true;
            }
        });

        passwordConferma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    continua();
                }
                return true;
            }
        });

        continua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continua();
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBack();
            }
        });
    }

    private void nasc(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, comuniRistretti);

        citta.setAdapter(adapter);
    }

    private void res(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, comuniRistretti);

        cittaStudio.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        checkBack();
    }

    private void checkBack() {
        switch (status) {
            case 0: {
                //se sono tutti vuoti non mandare l'avviso
                if (nome.getText().toString().isEmpty() &&
                        cognome.getText().toString().isEmpty() &&
                        dataNascita.getText().toString().isEmpty() &&
                        sesso.getCheckedRadioButtonId() == -1 &&
                        provincia.getText().toString().isEmpty() &&
                        citta.getText().toString().isEmpty() &&
                        cf.getText().toString().isEmpty() &&
                        provinciaStudio.getText().toString().isEmpty() &&
                        cittaStudio.getText().toString().isEmpty() &&
                        viaStudio.getText().toString().isEmpty() &&
                        email.getText().toString().isEmpty() &&
                        telefono.getText().toString().isEmpty() &&
                        password.getText().toString().isEmpty() &&
                        passwordConferma.getText().toString().isEmpty()
                ) finish();
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Annulla registrazione")
                            .setMessage("Sei sicuro di voler annullare la registrazione?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    hideKeyboard();
                                    finish();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("NO", null)
                            .setIcon(R.drawable.icon_alert)
                            .show();
                }
                break;
            }
            case 1: {
                status = 0;
                registrazione2.setVisibility(View.GONE);
                registrazione1.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                continua1.setText(btnTextContinua);
                status = 1;
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
                if (turno1[0].isChecked()) {
                    enable(0, true);
                } else {
                    disable(0, true);
                }
                break;
            case R.id.lavoroMarMed:
                if (turno1[1].isChecked()) {
                    enable(1, true);
                } else {
                    disable(1, true);
                }
                break;
            case R.id.lavoroMerMed:
                if (turno1[2].isChecked()) {
                    enable(2, true);
                } else {
                    disable(2, true);
                }
                break;
            case R.id.lavoroGioMed:
                if (turno1[3].isChecked()) {
                    enable(3, true);
                } else {
                    disable(3, true);
                }
                break;
            case R.id.lavoroVenMed:
                if (turno1[4].isChecked()) {
                    enable(4, true);
                } else {
                    disable(4, true);
                }
                break;
            case R.id.lavoroSabMed:
                if (turno1[5].isChecked()) {
                    enable(5, true);
                } else {
                    disable(5, true);
                }
                break;
            case R.id.lavoroDomMed:
                if (turno1[6].isChecked()) {
                    enable(6, true);
                } else {
                    disable(6, true);
                }
                break;
            case R.id.pausaLunMed:
                if (turno2[0].isChecked()) {
                    enable(0, false);
                } else {
                    disable(0, false);
                }
                break;
            case R.id.pausaMarMed:
                if (turno2[1].isChecked()) {
                    enable(1, false);
                } else {
                    disable(1, false);
                }
                break;
            case R.id.pausaMerMed:
                if (turno2[2].isChecked()) {
                    enable(2, false);
                } else {
                    disable(2, false);
                }
                break;
            case R.id.pausaGioMed:
                if (turno2[3].isChecked()) {
                    enable(3, false);
                } else {
                    disable(3, false);
                }
                break;
            case R.id.pausaVenMed:
                if (turno2[4].isChecked()) {
                    enable(4, false);
                } else {
                    disable(4, false);
                }
                break;
            case R.id.pausaSabMed:
                if (turno2[5].isChecked()) {
                    enable(5, false);
                } else {
                    disable(5, false);
                }
                break;
            case R.id.pausaDomMed:
                if (turno2[6].isChecked()) {
                    enable(6, false);
                } else {
                    disable(6, false);
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

    private void enable(int i, boolean turno) {

        if (turno) {
            settimanaMattina[i].setEnabled(true);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.greenDark));
        } else {
            turno2[i].setChecked(true);
            settimanaSera[i].setEnabled(true);
            settimanaSera[i].setTextColor(getResources().getColor(R.color.greenDark));
        }
    }

    private void disable(int i, boolean turno) {

        if (turno) {
            settimanaMattina[i].setEnabled(false);
            settimanaMattina[i].setTextColor(getResources().getColor(R.color.def));
        } else {
            turno2[i].setChecked(false);
            settimanaSera[i].setEnabled(false);
            settimanaSera[i].setTextColor(getResources().getColor(R.color.def));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int intHourOfDay, int intMinute) {
        String hourOfDay = String.valueOf(intHourOfDay), minute = String.valueOf(intMinute);
        if (intHourOfDay < 10) hourOfDay = '0' + String.valueOf(intHourOfDay);
        if (intMinute < 10) minute = '0' + String.valueOf(intMinute);
        if (count == 0) {
            if (mattina) settimanaMattina[idGiorno].setText(hourOfDay + ":" + minute);
            else settimanaSera[idGiorno].setText(hourOfDay + ":" + minute);
            count = 1;
        } else {
            String prevText;

            if (mattina) {
                prevText = settimanaMattina[idGiorno].getText().toString();
            } else {
                prevText = settimanaSera[idGiorno].getText().toString();
            }

            String nextText = hourOfDay + ":" + minute;

            if (stringToTime(nextText).before(stringToTime(prevText))) {
                String aux = prevText;
                prevText = nextText;
                nextText = aux;
            }

            if (mattina) settimanaMattina[idGiorno].setText(prevText + "\n" + nextText);
            else settimanaSera[idGiorno].setText(prevText + "\n" + nextText);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void registra() {

        intent = new Intent(RegistrazioneMedico.this, MainMedico.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                intent.putExtra("CF", global.medico.getCodiceFiscale());
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegistrazioneMedico.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrazioneMedico.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("table", "1");
                params.put("codicefiscale", global.medico.getCodiceFiscale());
                params.put("password", global.medico.getPassword());
                params.put("nome", global.medico.getNome());
                params.put("cognome", global.medico.getCognome());
                params.put("datanascita", "2000-03-18");

                if(global.medico.getSesso()) params.put("sesso", "Maschio");
                else params.put("sesso", "Femmina");

                params.put("provincianascita", global.medico.getIndirizzoNascita().getProvincia());
                params.put("cittanascita", global.medico.getIndirizzoNascita().getCitta());

                params.put("provinciastudio", global.medico.getIndirizzoStudio().getProvincia());
                params.put("cittastudio", global.medico.getIndirizzoStudio().getCitta());
                params.put("viastudio", global.medico.getIndirizzoStudio().getVia());

                params.put("email", email.getText().toString());
                params.put("telefono", global.medico.getTelefono());


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void continua() {
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

                        Indirizzo luogoNascita = new Indirizzo(provincia.getText().toString(), citta.getText().toString());
                        Indirizzo studio = new Indirizzo(provinciaStudio.getText().toString(), cittaStudio.getText().toString(), viaStudio.getText().toString());
                        global.medico = new Medico(nome.getText().toString(), cognome.getText().toString(),
                                dataNascita.getText().toString(), boolMaschio, cf.getText().toString().toUpperCase(),
                                luogoNascita, studio, password.getText().toString(),
                                email.getText().toString(), telefono.getText().toString());

                        registra();
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


    private void calcoloCodiceFiscale () {
        String codFis = "";
        String cognomecf = cognome.getText().toString().toUpperCase();
        String nomecf = nome.getText().toString().toUpperCase();
        String dataNascitacf = dataNascita.getText().toString();
        String comuneNascitacf = citta.getText().toString();


        /*calcolo prime 3 lettere */
        int cont = 0;
        /*caso cognome minore di 3 lettere*/
        if (cognomecf.length()<3){
            codFis+= cognomecf;
            while (codFis.length()<3) codFis+= "X";
            cont=3;
        }
        /*caso normale*/
        for (int i=0;i<cognomecf.length();i++) {
            if (cont==3) break;
            if (cognomecf.charAt(i)!='A' && cognomecf.charAt(i)!='E' &&
                    cognomecf.charAt(i)!='I' && cognomecf.charAt(i)!='O' &&
                    cognomecf.charAt(i)!='U') {
                codFis+= Character.toString(cognomecf.charAt(i));
                cont++;
            }
        }
        /* nel casoci siano meno di 3 consonanti*/
        while (cont<3) {
            for (int i=0;i<cognomecf.length();i++) {
                if (cont==3) break;
                if (cognomecf.charAt(i)=='A' || cognomecf.charAt(i)=='E' ||
                        cognomecf.charAt(i)=='I' || cognomecf.charAt(i)=='O' ||
                        cognomecf.charAt(i)=='U') {
                    codFis+= Character.toString(cognomecf.charAt(i));
                    cont++;
                }
            }
        }
        /*lettere nome*/
        cont = 0;
        /*caso nome minore di 3 lettere*/
        if (nomecf.length()<3){
            codFis+= nomecf;
            while (codFis.length()<6) codFis+= "X";
            cont=3;
        }
        /*caso normale*/
        for (int i=0;i<nomecf.length();i++) {
            if (nomecf.charAt(i)!='A' && nomecf.charAt(i)!='E' &&
                    nomecf.charAt(i)!='I' && nomecf.charAt(i)!='O' &&
                    nomecf.charAt(i)!='U') {
                cont++;
            }
        }

        if(cont==3){
            for (int i=0;i<nomecf.length();i++) {
                if (nomecf.charAt(i)!='A' && nomecf.charAt(i)!='E' &&
                        nomecf.charAt(i)!='I' && nomecf.charAt(i)!='O' &&
                        nomecf.charAt(i)!='U') {
                    codFis+= Character.toString(nomecf.charAt(i));
                }
            }
        }

        /* nel caso ci siano meno di 3 consonanti*/
        while (cont<3) {
            for (int i=0;i<nomecf.length();i++) {
                if (cont==3) break;
                if (nomecf.charAt(i)=='A' || nomecf.charAt(i)=='E' ||
                        nomecf.charAt(i)=='I' || nomecf.charAt(i)=='O' ||
                        nomecf.charAt(i)=='U') {
                    codFis+= Character.toString(nomecf.charAt(i));
                    cont++;
                }
            }
        }

        /* nel caso ci siano piu di 3 consonanti*/
        if(cont>3){
            int c = 0;
            for (int i=0;i<nomecf.length();i++) {
                if(c==4) break;
                if (nomecf.charAt(i)!='A' && nomecf.charAt(i)!='E' &&
                        nomecf.charAt(i)!='I' && nomecf.charAt(i)!='O' &&
                        nomecf.charAt(i)!='U') {
                    if(c!=1){
                        codFis+= Character.toString(nomecf.charAt(i));
                    }
                    c++;
                }
            }
        }

        /* anno */
        codFis+=dataNascitacf.substring(8,10);
        /*Mese*/
        int mese=0;
        if (dataNascitacf.charAt(3)== '0') mese = Integer.parseInt(dataNascitacf.substring(4,5));
        else mese = Integer.parseInt(dataNascitacf.substring(3,5));
        switch (mese) {
            case 1: {codFis+="A";break;}
            case 2: {codFis+="B";break;}
            case 3: {codFis+="C";break;}
            case 4: {codFis+="D";break;}
            case 5: {codFis+="E";break;}
            case 6: {codFis+="H";break;}
            case 7: {codFis+="L";break;}
            case 8: {codFis+="M";break;}
            case 9: {codFis+="P";break;}
            case 10: {codFis+="R";break;}
            case 11: {codFis+="S";break;}
            case 12: {codFis+="T";break;}
        }
        /*giorno*/
        int giorno=0;
        if (dataNascitacf.charAt(0)== '0') giorno = Integer.parseInt(dataNascitacf.substring(1,2));
        else giorno = Integer.parseInt(dataNascitacf.substring(0,2));
        if (sesso.getCheckedRadioButtonId() == R.id.maschioMed) codFis+= giorno;
        else {
            giorno+=40;
            codFis+=Integer.toString(giorno);
        }
        /*Comune*/
        int pos = global.comuni.indexOf(comuneNascitacf);
        String codCom = global.codiceComuni.get(pos);
        codFis+=codCom;

        /*Carattere di controllo*/
        int sommaPari=0;
        for (int i=1;i<=13;i+=2) {
            switch (codFis.charAt(i)) {
                case '0': {sommaPari+=0;break;}
                case '1': {sommaPari+=1;break;}
                case '2': {sommaPari+=2;break;}
                case '3': {sommaPari+=3;break;}
                case '4': {sommaPari+=4;break;}
                case '5': {sommaPari+=5;break;}
                case '6': {sommaPari+=6;break;}
                case '7': {sommaPari+=7;break;}
                case '8': {sommaPari+=8;break;}
                case '9': {sommaPari+=9;break;}
                case 'A': {sommaPari+=0;break;}
                case 'B': {sommaPari+=1;break;}
                case 'C': {sommaPari+=2;break;}
                case 'D': {sommaPari+=3;break;}
                case 'E': {sommaPari+=4;break;}
                case 'F': {sommaPari+=5;break;}
                case 'G': {sommaPari+=6;break;}
                case 'H': {sommaPari+=7;break;}
                case 'I': {sommaPari+=8;break;}
                case 'J': {sommaPari+=9;break;}
                case 'K': {sommaPari+=10;break;}
                case 'L': {sommaPari+=11;break;}
                case 'M': {sommaPari+=12;break;}
                case 'N': {sommaPari+=13;break;}
                case 'O': {sommaPari+=14;break;}
                case 'P': {sommaPari+=15;break;}
                case 'Q': {sommaPari+=16;break;}
                case 'R': {sommaPari+=17;break;}
                case 'S': {sommaPari+=18;break;}
                case 'T': {sommaPari+=19;break;}
                case 'U': {sommaPari+=20;break;}
                case 'V': {sommaPari+=21;break;}
                case 'W': {sommaPari+=22;break;}
                case 'X': {sommaPari+=23;break;}
                case 'Y': {sommaPari+=24;break;}
                case 'Z': {sommaPari+=25;break;}
            }
        }
        int sommaDispari=0;
        for (int i=0;i<=14;i+=2) {
            switch (codFis.charAt(i)) {
                case '0': {sommaDispari+=1;break;}
                case '1': {sommaDispari+=0;break;}
                case '2': {sommaDispari+=5;break;}
                case '3': {sommaDispari+=7;break;}
                case '4': {sommaDispari+=9;break;}
                case '5': {sommaDispari+=13;break;}
                case '6': {sommaDispari+=15;break;}
                case '7': {sommaDispari+=17;break;}
                case '8': {sommaDispari+=19;break;}
                case '9': {sommaDispari+=21;break;}
                case 'A': {sommaDispari+=1;break;}
                case 'B': {sommaDispari+=0;break;}
                case 'C': {sommaDispari+=5;break;}
                case 'D': {sommaDispari+=7;break;}
                case 'E': {sommaDispari+=9;break;}
                case 'F': {sommaDispari+=13;break;}
                case 'G': {sommaDispari+=15;break;}
                case 'H': {sommaDispari+=17;break;}
                case 'I': {sommaDispari+=19;break;}
                case 'J': {sommaDispari+=21;break;}
                case 'K': {sommaDispari+=2;break;}
                case 'L': {sommaDispari+=4;break;}
                case 'M': {sommaDispari+=18;break;}
                case 'N': {sommaDispari+=20;break;}
                case 'O': {sommaDispari+=11;break;}
                case 'P': {sommaDispari+=3;break;}
                case 'Q': {sommaDispari+=6;break;}
                case 'R': {sommaDispari+=8;break;}
                case 'S': {sommaDispari+=12;break;}
                case 'T': {sommaDispari+=14;break;}
                case 'U': {sommaDispari+=16;break;}
                case 'V': {sommaDispari+=10;break;}
                case 'W': {sommaDispari+=22;break;}
                case 'X': {sommaDispari+=25;break;}
                case 'Y': {sommaDispari+=24;break;}
                case 'Z': {sommaDispari+=23;break;}
            }
        }
        int interoControllo = (sommaPari+sommaDispari)%26;
        String carattereControllo="";
        switch (interoControllo) {
            case 0:{carattereControllo="A";break;}
            case 1:{carattereControllo="B";break;}
            case 2:{carattereControllo="C";break;}
            case 3:{carattereControllo="D";break;}
            case 4:{carattereControllo="E";break;}
            case 5:{carattereControllo="F";break;}
            case 6:{carattereControllo="G";break;}
            case 7:{carattereControllo="H";break;}
            case 8:{carattereControllo="I";break;}
            case 9:{carattereControllo="J";break;}
            case 10:{carattereControllo="K";break;}
            case 11:{carattereControllo="L";break;}
            case 12:{carattereControllo="M";break;}
            case 13:{carattereControllo="N";break;}
            case 14:{carattereControllo="O";break;}
            case 15:{carattereControllo="P";break;}
            case 16:{carattereControllo="Q";break;}
            case 17:{carattereControllo="R";break;}
            case 18:{carattereControllo="S";break;}
            case 19:{carattereControllo="T";break;}
            case 20:{carattereControllo="U";break;}
            case 21:{carattereControllo="V";break;}
            case 22:{carattereControllo="W";break;}
            case 23:{carattereControllo="X";break;}
            case 24:{carattereControllo="Y";break;}
            case 25:{carattereControllo="Z";break;}
        }
        codFis+=carattereControllo;
        cf.setText(codFis);
    }

    private void checkAPI(){
        //CONTROLLA LE API DEL TELEFONO, SE MAGGIORI DI MARSHMELLOW MODIFICA IL COLORE DEL TESTO DELLA NOTIFICATION BAR IN CHIARO
        //ALTRIMENTI SE E' INFERIORE ALLE API 23 MODIFICA LA NOTIFICATION BAR IN COLORE SCURO (IN QUANTO NON PUO MODIFICARE IL COLORE DEL TESTO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(), R.color.colorAccent));
        }
    }
}
