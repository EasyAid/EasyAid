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
import com.easy.aid.Class.Ricetta;
import com.easy.aid.Medico.MainMedico;
import com.easy.aid.Medico.RegistrazioneMedico;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RegistrazionePaziente extends AppCompatActivity {


    private String btnTextContinua = "CONTINUA";
    private String btnTextFinisci = "CONFERMA REGISTRAZIONE";

    private NetVariables global;
    private EditText nome, cognome;
    private EditText codiceFiscale, viaResidenza;
    private EditText email, password, confermaPassword;
    private TextView dataNascita;
    private AutoCompleteTextView autoCompleteProvinciaNascita, autoCompleteCittaNascita, autoCompleteProvinciaResidenza, autoCompleteCittaResidenza, autoCompleteMedicoBase;
    private Button registrazioneButton;
    private ImageView showCalendar;
    private ImageView back;
    private NetVariables c;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private RadioGroup sessoRadio;
    private RadioButton maschio, femmina;
    private String sesso;
    private Intent intent;
    private List<String> medici;
    private List<String> comuniRistretti;

    private ScrollView[] registrazione;
    private int step = 0;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_registrazione);

        checkAPI();
        global = (NetVariables) this.getApplication();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        c = ((NetVariables) this.getApplication());

        registrazione = new ScrollView[3];

        registrazione[0] = findViewById(R.id.layout0RegistrazionePaziente);
        registrazione[1] = findViewById(R.id.layout1RegistrazionePaziente);
        registrazione[2] = findViewById(R.id.layout2RegistrazionePaziente);

        registrazioneButton = findViewById(R.id.continuaPaziente);

        nome = findViewById(R.id.editNomePaziente);
        cognome = findViewById(R.id.editCognomePaziente);
        dataNascita = findViewById(R.id.editDataNascitaPaziente);
        autoCompleteProvinciaNascita = findViewById(R.id.editProvinciaNascPaziente);
        autoCompleteCittaNascita = findViewById(R.id.editCittaNascPaziente);
        back = findViewById(R.id.backRegistrazionePaziente);
        showCalendar = findViewById(R.id.showCalendarPaziente);
        codiceFiscale = findViewById(R.id.editCodiceFiscaleRegistrazionePaziente);
        maschio = findViewById(R.id.maschioRegistrazionePaziente);
        femmina = findViewById(R.id.femminaRegistrazionePaziente);
        sessoRadio = findViewById(R.id.sessoRadioRegistrazionewPaziente);
        autoCompleteProvinciaResidenza = findViewById(R.id.editProvinciaResidenzaRegistrazionePaziente);
        autoCompleteCittaResidenza = findViewById(R.id.editCittaResidenzaRegistrazionePaziente);
        autoCompleteMedicoBase = findViewById(R.id.medicoBaseRegistrazionePaziente);
        viaResidenza = findViewById(R.id.editViaResidenzaRegistrazionePaziente);
        email = findViewById(R.id.indirizzoEmailRegistrazionePaziente);
        password = findViewById(R.id.editPasswordRegistrazionePaziente);
        confermaPassword = findViewById(R.id.editConfermaPasswordRegistrazionePaziente);

        medici = new ArrayList<>();
        comuniRistretti = new ArrayList<>();


        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

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

        autoCompleteProvinciaNascita.setThreshold(1);//will start working from first character
        autoCompleteProvinciaNascita.setAdapter(adapter);
        autoCompleteProvinciaResidenza.setThreshold(1);//will start working from first character
        autoCompleteProvinciaResidenza.setAdapter(adapter);

        autoCompleteProvinciaNascita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                comuniRistretti.clear();

                int pos = global.province.indexOf(autoCompleteProvinciaNascita.getText().toString());
                String sigla = global.siglaProvince.get(pos);
                for(int i=0;i<global.siglaProvinceComuni.size();i++){
                    if(sigla.equals(global.siglaProvinceComuni.get(i))){
                        comuniRistretti.add(global.comuni.get(i));
                    }
                }

                nasc();

            }
        });

        autoCompleteProvinciaResidenza.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                comuniRistretti.clear();

                int pos = global.province.indexOf(autoCompleteProvinciaResidenza.getText().toString());
                String sigla = global.siglaProvince.get(pos);
                for(int i=0;i<global.siglaProvinceComuni.size();i++){
                    if(sigla.equals(global.siglaProvinceComuni.get(i))){
                        comuniRistretti.add(global.comuni.get(i));
                    }
                }

                res();

            }
        });

        autoCompleteCittaNascita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calcoloCodiceFiscale();
            }
        });

        dataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                new DatePickerDialog(RegistrazionePaziente.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        codiceFiscale.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    continuaFUN();
                }

                return true;
            }
        });

        autoCompleteMedicoBase.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    continuaFUN();
                }

                return true;
            }
        });

        confermaPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    continuaFUN();
                }

                return true;
            }
        });

        registrazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuaFUN();
            }
        });

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

    private void nasc(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, comuniRistretti);

        autoCompleteCittaNascita.setAdapter(adapter);
    }

    private void res(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, comuniRistretti);

        autoCompleteCittaResidenza.setAdapter(adapter);
    }

    private void showError(int st) {
        if (st == 0) {
            error = true;
            registrazione[2].setVisibility(View.GONE);
            registrazione[1].setVisibility(View.GONE);
            registrazione[0].setVisibility(View.VISIBLE);
            registrazioneButton.setText(btnTextContinua);
            step = 0;
        } else if (st == 1) {
            error = true;
            registrazione[2].setVisibility(View.GONE);
            registrazione[0].setVisibility(View.GONE);
            registrazione[1].setVisibility(View.VISIBLE);
            registrazioneButton.setText(btnTextContinua);
            step = 0;
        } else if (st == 2) {
            error = true;
            step = 2;
        }
    }

    @Override
    public void onBackPressed() {
        checkBack();
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


    private void continuaFUN() {
        if (step == 2) {
            error = false;
            if (confermaPassword.getText().toString().isEmpty()) {
                confermaPassword.setError("Inserisci password di controllo");
                showError(2);
            }
            if (password.getText().toString().isEmpty()) {
                password.setError("Inserisci password");
                showError(2);
            }
            if (email.getText().toString().isEmpty()) {
                email.setError("Inserisci un email");
                showError(2);
            }

            if (autoCompleteMedicoBase.getText().toString().isEmpty()) {
                autoCompleteMedicoBase.setError("Inserisci un medico di base");
                showError(1);
            }

            if (nome.getText().toString().isEmpty()) {
                nome.setError("Inserisci nome");
                showError(0);
            }
            if (cognome.getText().toString().isEmpty()) {
                cognome.setError("Inserisci cognome");
                showError(0);
            }
            if (dataNascita.getText().toString().isEmpty()) {
                dataNascita.setError("Inserisci data di nascita");
                showError(0);
            }
            if (autoCompleteProvinciaNascita.getText().toString().isEmpty()) {
                autoCompleteProvinciaNascita.setError("Inserisci provincia di nascita");
                showError(0);
            }
            if (autoCompleteCittaNascita.getText().toString().isEmpty()) {
                autoCompleteCittaNascita.setError("Inserisci città di nascita");
                showError(0);
            }
            if (codiceFiscale.getText().toString().isEmpty()) {
                codiceFiscale.setError("Inserisci il codice fiscale");
                showError(0);
            }

            /**
             * REGISTRAZIONE
             */
            if (!error) {
                Registrazione();
            }
        } else if (step == 1) {
            hideKeyboard();
            registrazioneButton.setText(btnTextFinisci);
            registrazione[1].setVisibility(View.GONE);
            registrazione[2].setVisibility(View.VISIBLE);
            step++;
        } else if (step == 0) {
            hideKeyboard();
            registrazione[0].setVisibility(View.GONE);
            registrazione[1].setVisibility(View.VISIBLE);
            step++;
        }
    }

    private void checkBack() {
        switch (step) {
            case 0: {

                if (    nome.getText().toString().isEmpty() &&
                        cognome.getText().toString().isEmpty() &&
                        dataNascita.getText().toString().isEmpty() &&
                        sessoRadio.getCheckedRadioButtonId() == -1 &&
                        autoCompleteProvinciaNascita.getText().toString().isEmpty() &&
                        autoCompleteCittaNascita.getText().toString().isEmpty() &&
                        codiceFiscale.getText().toString().isEmpty() &&
                        autoCompleteProvinciaResidenza.getText().toString().isEmpty() &&
                        autoCompleteCittaResidenza.getText().toString().isEmpty() &&
                        viaResidenza.getText().toString().isEmpty() &&
                        autoCompleteMedicoBase.getText().toString().isEmpty() &&
                        email.getText().toString().isEmpty() &&
                        password.getText().toString().isEmpty() &&
                        confermaPassword.getText().toString().isEmpty()
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


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void Registrazione() {

        intent = new Intent(RegistrazionePaziente.this, MainPaziente.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (sessoRadio.getCheckedRadioButtonId() == maschio.getId()) {
            sesso = "Maschio";
        } else {
            sesso = "Femmina";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        intent.putExtra("CF", codiceFiscale.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrazionePaziente.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome.getText().toString());
                params.put("cognome", cognome.getText().toString());
                params.put("datanascita", "2019-03-13");
                params.put("sesso", sesso);
                params.put("provincianascita", autoCompleteProvinciaNascita.getText().toString());
                params.put("cittanascita", autoCompleteCittaNascita.getText().toString());
                params.put("codicefiscale", codiceFiscale.getText().toString());

                params.put("provinciaresidenza", autoCompleteProvinciaResidenza.getText().toString());
                params.put("cittaresidenza", autoCompleteCittaResidenza.getText().toString());
                params.put("viaresidenza", viaResidenza.getText().toString());
                params.put("idmedicobase", "2");

                params.put("email", email.getText().toString());
                params.put("password", md5(password.getText().toString()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }







    private void calcoloCodiceFiscale () {
        String codFis = "";
        String cognomecf = cognome.getText().toString().toUpperCase();
        String nomecf = nome.getText().toString().toUpperCase();
        String dataNascitacf = dataNascita.getText().toString();
        String comuneNascitacf = autoCompleteCittaNascita.getText().toString();


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
        if (sessoRadio.getCheckedRadioButtonId() == R.id.maschioRegistrazionePaziente) codFis+= giorno;
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
        codiceFiscale.setText(codFis);
    }
}
