package com.easy.aid.Medico;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.Medico;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Medico.AccessoMedico;
import com.easy.aid.Medico.MainMedico;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ragosta
 * pagina di accesso del medico
 */
public class AccessoMedico extends AppCompatActivity {

    private EditText cf, pwd;
    private Button accedi, registrazione;
    private CheckBox restaConnesso;
    private static String URL_LOGIN = NetVariables.URL_LOGIN;
    private Intent intent;
    private ImageView back;
    private NetVariables global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (NetVariables)this.getApplication();
        setContentView(R.layout.medico_accesso);

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

        accedi = findViewById(R.id.accessoButtonMed);
        registrazione = findViewById(R.id.registrazioneButtonMed);

        pwd = findViewById(R.id.accessoPasswordMed);
        cf = findViewById(R.id.accessoCodiceFiscaleMed);
        restaConnesso = findViewById(R.id.restaConnessoMed);

        back = findViewById(R.id.backAccessoMed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                accedi();
            }
        });

        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;
                intent = new Intent(AccessoMedico.this, RegistrazioneMedico.class);
                startActivity(intent);
            }
        });

    }

    private void Login(final String sCF, final String sPass) {
        intent = new Intent(AccessoMedico.this, MainMedico.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                if(restaConnesso.isChecked()){
                                    global.prefs.edit().putString("CF", sCF).apply();
                                    global.prefs.edit().putString("settore", "Medico").apply();
                                }

                                intent.putExtra("CF", sCF);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AccessoMedico.this, "Dati errati", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AccessoMedico.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccessoMedico.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("table", "1");
                params.put("cf", sCF);
                params.put("password", sPass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void accedi(){
        if (!cf.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty()) {
            //giusto
            Login(cf.getText().toString(), pwd.getText().toString());

        } else {
            cf.setError("Inserire codice fiscale");
            pwd.setError("Inserire password");
        }
    }
}
