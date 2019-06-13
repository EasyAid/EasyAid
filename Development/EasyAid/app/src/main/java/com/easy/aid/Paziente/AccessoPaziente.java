package com.easy.aid.Paziente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.MCrypt;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.easy.aid.Class.NetVariables.URL_LOGIN_AES;

/**
 *
 * @since 1.0 07/02/2019
 * @version 1.0
 *
 * @author Tironi
 *
 */

public class AccessoPaziente extends AppCompatActivity {

    private EditText cf, pwd;
    private Button accedi, registrazione;
    private static String URL_LOGIN = "http://99.80.72.24/login.php";
    private TextInputLayout layoutPass;
    private Intent intent;
    private NetVariables global;
    private ImageView back;
    private CheckBox restaConnesso;
    private MCrypt crypt;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        global = (NetVariables) this.getApplication();
        crypt = new MCrypt();

        setContentView(R.layout.paziente_accesso);

        checkAPI();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        cf              = findViewById(R.id.accessoCodiceFiscalePaz);
        pwd             = findViewById(R.id.accessoPasswordPaz);
        accedi          = findViewById(R.id.accessoButtonPaz);
        registrazione   = findViewById(R.id.registrazioneButtonPaz);
        back            = findViewById(R.id.backAccessoPaz);
        layoutPass      = findViewById(R.id.layoutAccessoPasswordPaz);
        restaConnesso   = findViewById(R.id.restaConnessoPaziente);

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                layoutPass.setPasswordVisibilityToggleEnabled(true);
            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;

                String sCF = cf.getText().toString().trim();
                String sPass = md5(pwd.getText().toString().trim());

                if(!sCF.isEmpty() && !sPass.isEmpty()){
                    Login(sCF,sPass);
                }else{
                    layoutPass.setPasswordVisibilityToggleEnabled(false);
                    cf.setError("Inserire codice fiscale");
                    pwd.setError("Inserire password");
                }
            }
        });

        //APRE L' ACTIVITY REGISTRAZIONE PAZIENTE
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;

                Intent i = new Intent(AccessoPaziente.this, RegistrazionePaziente.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                finish();
            }
        });
    }

    private void Login(final String sCF, final String sPass) {
        intent = new Intent(AccessoPaziente.this, MainPaziente.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN_AES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){

                                if(restaConnesso.isChecked()){
                                    global.prefs.edit().putString("CF", sCF).apply();
                                    global.prefs.edit().putString("settore", "Paziente").apply();
                                }

                                intent.putExtra("CF", sCF);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(AccessoPaziente.this, "Dati errati", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AccessoPaziente.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccessoPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                try {
                    s = MCrypt.bytesToHex( crypt.encrypt("0") );
                    params.put("table", s);
                    s = MCrypt.bytesToHex( crypt.encrypt(sCF) );
                    params.put("cf", s);
                    s = MCrypt.bytesToHex( crypt.encrypt(sPass) );
                    params.put("password", s);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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


}






