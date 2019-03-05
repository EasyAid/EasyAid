package com.easy.aid.Paziente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private static String URL_LOGIN = "http://192.168.1.9/codiceplastico/login.php";

    private Intent intent;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_accesso);


        cf            = (EditText) findViewById(R.id.accessoCodiceFiscalePaz);
        pwd             = (EditText) findViewById(R.id.accessoPasswordPaz);
        accedi          = (Button)   findViewById(R.id.accessoButtonPaz);
        registrazione   = (Button)   findViewById(R.id.registrazioneButtonPaz);
        back            = (ImageView)findViewById(R.id.backAccessoPaz);


        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCF = cf.getText().toString().trim();
                String sPass = pwd.getText().toString().trim();

                if(!sCF.isEmpty() && !sPass.isEmpty()){
                    Login(sCF,sPass);
                }else{
                    cf.setError("Please insert email");
                    pwd.setError("Please insert password");
                }
            }
        });

        //APRE L' ACTIVITY REGISTRAZIONE PAZIENTE
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccessoPaziente.this, RegistrazionePaziente.class);
                startActivity(i);
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
        intent = new Intent(AccessoPaziente.this, MainPaziente.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AccessoPaziente.this, "Error " + e.toString() , Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cf", sCF);
                params.put("password", sPass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
