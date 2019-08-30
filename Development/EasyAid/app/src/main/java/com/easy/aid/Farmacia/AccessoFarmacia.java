package com.easy.aid.Farmacia;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.easy.aid.Class.NetVariables;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccessoFarmacia extends AppCompatActivity {

    private EditText cf, pwd;
    private Button accedi, registrazione;
    private static String URL_LOGIN = "http://99.80.72.24/login.php";
    private Intent intent;
    private TextInputLayout layoutPass;
    private NetVariables global;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_accesso);

        global = (NetVariables) (this).getApplication();


        checkAPI();

        accedi = findViewById(R.id.accessoButtonFarm);
        registrazione = findViewById(R.id.registrazioneButtonFarm);
        pwd = findViewById(R.id.accessoPasswordFarm);
        cf = findViewById(R.id.accessoCodiceFarmacia);
        back = findViewById(R.id.backAccessoFarm);
        layoutPass = findViewById(R.id.layoutAccessoPasswordFarm);
        back = findViewById(R.id.backAccessoFarm);

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.checktime()) return;

                String sCF = cf.getText().toString().trim();
                String sPass = pwd.getText().toString().trim();

                if (!sCF.isEmpty() && !sPass.isEmpty()) {
                    Login(sCF, sPass);
                } else {
                    layoutPass.setPasswordVisibilityToggleEnabled(false);
                    cf.setError("Inserire mail");
                    pwd.setError("Inserire password");
                }
            }
        });

        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AccessoFarmacia.this, RegistrazioneFarmacia.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Login(final String sCF, final String sPass) {
        intent = new Intent(AccessoFarmacia.this, MainFarmacia.class);
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
                                intent.putExtra("Email", sCF);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AccessoFarmacia.this, "Dati errati", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AccessoFarmacia.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccessoFarmacia.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("table", "2");
                params.put("cf", sCF);
                params.put("password", sPass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void checkAPI() {
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
