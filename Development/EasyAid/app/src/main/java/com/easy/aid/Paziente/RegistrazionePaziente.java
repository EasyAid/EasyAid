package com.easy.aid.Paziente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrazionePaziente extends AppCompatActivity {


    private String btnTextContinua = "CONTINUA";
    private String btnTextFinisci = "CONFERMA REGISTRAZIONE";

    private EditText nome, cognome;
    private EditText codiceFiscale, viaResidenza;
    private EditText email, password, confermaPassword, dataNascita;
    private AutoCompleteTextView provinciaNascita, cittaNascita, provinciaResidenza, cittaResidenza, medicoBase;
    private Button registrazioneButton;
    private ImageView showCalendar;
    private ImageView back;
    private NetVariables c;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private RadioGroup sessoRadio;
    private RadioButton maschio, femmina;
    private String sesso;

    private ScrollView[] registrazione;
    private int step=0;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_registrazione);

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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        c  = ((NetVariables) this.getApplication());

        registrazione = new ScrollView[3];

        registrazione[0] = (ScrollView) findViewById(R.id.layout0RegistrazionePaziente);
        registrazione[1] = (ScrollView) findViewById(R.id.layout1RegistrazionePaziente);
        registrazione[2] = (ScrollView) findViewById(R.id.layout2RegistrazionePaziente);

        registrazioneButton   = (Button) findViewById(R.id.continuaPaziente);

        nome = (EditText) findViewById(R.id.editNomePaziente);
        cognome = (EditText) findViewById(R.id.editCognomePaziente);
        dataNascita = (EditText) findViewById(R.id.editDataNascitaPaziente);
        provinciaNascita = (AutoCompleteTextView) findViewById(R.id.editProvinciaNascPaziente);
        cittaNascita = (AutoCompleteTextView) findViewById(R.id.editCittaNascPaziente);
        back = (ImageView)findViewById(R.id.backRegistrazionePaziente);
        showCalendar = (ImageView) findViewById(R.id.showCalendarPaziente);
        codiceFiscale = (EditText) findViewById(R.id.editCodiceFiscaleRegistrazionePaziente);
        maschio = (RadioButton) findViewById(R.id.maschioRegistrazionePaziente);
        femmina = (RadioButton) findViewById(R.id.femminaRegistrazionePaziente);
        sessoRadio = (RadioGroup) findViewById(R.id.sessoRadioRegistrazionewPaziente);
        provinciaResidenza = (AutoCompleteTextView) findViewById(R.id.editProvinciaResidenzaRegistrazionePaziente);
        cittaResidenza = (AutoCompleteTextView) findViewById(R.id.editCittaResidenzaRegistrazionePaziente);
        medicoBase = (AutoCompleteTextView) findViewById(R.id.medicoBaseRegistrazionePaziente);
        viaResidenza = (EditText) findViewById(R.id.editViaResidenzaRegistrazionePaziente);
        email = (EditText) findViewById(R.id.indirizzoEmailRegistrazionePaziente);
        password = (EditText) findViewById(R.id.editPasswordRegistrazionePaziente);
        confermaPassword = (EditText) findViewById(R.id.editConfermaPasswordRegistrazionePaziente);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        showCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();

                new DatePickerDialog(RegistrazionePaziente.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //TODO CONTROLLA VALIDITA DELLA DATA DI NASCITA

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, c.province);

        provinciaNascita.setThreshold(1);//will start working from first character
        provinciaNascita.setAdapter(adapter);

        dataNascita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(start==1||start==4){
                    dataNascita.setText((dataNascita.getText().toString()+"/"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registrazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step == 2){
                    error = false;
                    if(confermaPassword.getText().toString().isEmpty()){
                        confermaPassword.setError("Inserisci password di controllo");
                        showError(2);
                    }
                    if(password.getText().toString().isEmpty()){
                        password.setError("Inserisci password");
                        showError(2);
                    }
                    if(email.getText().toString().isEmpty()){
                        email.setError("Inserisci un email");
                        showError(2);
                    }

                    if(medicoBase.getText().toString().isEmpty()){
                        medicoBase.setError("Inserisci un medico di base");
                        showError(1);
                    }

                    if(nome.getText().toString().isEmpty()){
                        nome.setError("Inserisci nome");
                        showError(0);
                    }
                    if(cognome.getText().toString().isEmpty()){
                        cognome.setError("Inserisci cognome");
                        showError(0);
                    }
                    if(dataNascita.getText().toString().isEmpty()){
                        dataNascita.setError("Inserisci data di nascita");
                        showError(0);
                    }
                    if(provinciaNascita.getText().toString().isEmpty()){
                        provinciaNascita.setError("Inserisci provincia di nascita");
                        showError(0);
                    }
                    if(cittaNascita.getText().toString().isEmpty()){
                        cittaNascita.setError("Inserisci città di nascita");
                        showError(0);
                    }
                    if(codiceFiscale.getText().toString().isEmpty()){
                        codiceFiscale.setError("Inserisci il codice fiscale");
                        showError(0);
                    }

                    /**
                     * REGISTRAZIONE
                     */
                    if(!error){
                        Registrazione();
                    }
                }else if(step == 1){
                    hideKeyboard();
                    registrazioneButton.setText(btnTextFinisci);
                    registrazione[1].setVisibility(View.GONE);
                    registrazione[2].setVisibility(View.VISIBLE);
                    step++;
                }else if(step == 0){
                    hideKeyboard();
                    registrazione[0].setVisibility(View.GONE);
                    registrazione[1].setVisibility(View.VISIBLE);
                    step++;
                }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBack();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);

        dataNascita.setText(sdf.format(myCalendar.getTime()));
    }

    private void showError(int st){
        if(st == 0){
            error = true;
            registrazione[2].setVisibility(View.GONE);
            registrazione[1].setVisibility(View.GONE);
            registrazione[0].setVisibility(View.VISIBLE);
            registrazioneButton.setText(btnTextContinua);
            step = 0;
        }else if(st == 1){
            error = true;
            registrazione[2].setVisibility(View.GONE);
            registrazione[0].setVisibility(View.GONE);
            registrazione[1].setVisibility(View.VISIBLE);
            registrazioneButton.setText(btnTextContinua);
            step = 0;
        }else if(st == 2){
            error = true;
            step = 2;
        }
    }

    @Override
    public void onBackPressed() {
        checkBack();
    }

    private void checkBack(){
        switch (step) {
            case 0: {

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

                break;
            }
            case 1: {
                step--;
                registrazione[1].setVisibility(View.GONE);
                registrazione[0].setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                registrazioneButton.setText(btnTextContinua);
                step--;
                registrazione[2].setVisibility(View.GONE);
                registrazione[1].setVisibility(View.VISIBLE);
                break;
            }
        }
    }


    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void Registrazione(){

        if(sessoRadio.getCheckedRadioButtonId() == maschio.getId()){
            sesso = "Maschio";
        }else{
            sesso = "Femmina";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrazionePaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome.getText().toString());
                params.put("cognome", cognome.getText().toString());
                params.put("datanascita", "2019-03-13");
                params.put("sesso", sesso);
                params.put("provincianascita", provinciaNascita.getText().toString());
                params.put("cittanascita", cittaNascita.getText().toString());
                params.put("codicefiscale", codiceFiscale.getText().toString());

                params.put("provinciaresidenza", provinciaResidenza.getText().toString());
                params.put("cittaresidenza", cittaResidenza.getText().toString());
                params.put("viaresidenza", viaResidenza.getText().toString());
                params.put("idmedicobase", "1");

                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
