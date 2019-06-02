package com.easy.aid.Paziente;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.AdapterOrdinaFarmaco;
import com.easy.aid.Class.Card.CardFragmentPagerAdapter;
import com.easy.aid.Class.Farmaco;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.Class.Card.ShadowTransformer;
import com.easy.aid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdinaRicettaPaziente extends AppCompatActivity {

    private NetVariables global;

    private boolean pagato = false;

    private RecyclerView recyclerView;
    private AdapterOrdinaFarmaco adapterOrdinaFarmaco;

    private RelativeLayout primo, secondo;
    private Button continua, invia;
    private TextView ordinaDocumento;
    private List<Ricetta> ordine;
    private double tot = 0;
    private Button paga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_ordina_ricetta);

        global = (NetVariables) this.getApplication();
        ordine = new ArrayList<>();
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        continua = findViewById(R.id.continuaOrdinaFarmacoPaziente);
        primo = findViewById(R.id.primoOrdinaFarmacia);
        secondo = findViewById(R.id.secondoOrdinaFarmacia);
        invia = findViewById(R.id.inviaOrdineRicettaPaziente);
        paga = findViewById(R.id.pagaOra);
        ordinaDocumento = findViewById(R.id.ordineOrdineRicetta);
        recyclerView = findViewById(R.id.recyclerViewOrdinaFarmaco);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterOrdinaFarmaco = new AdapterOrdinaFarmaco(0, global.ricette, global.farmaciID, getApplicationContext(), ordine);
        recyclerView.setAdapter(adapterOrdinaFarmaco);


        paga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagato=true;
                Toast.makeText(getApplicationContext(), "Ordine pagato", Toast.LENGTH_SHORT).show();
            }
        });

        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordine.size() != 0) {
                    primo.setVisibility(View.GONE);
                    secondo.setVisibility(View.VISIBLE);

                    for (int i = 0; i < ordine.size(); i++) {
                        String old = ordinaDocumento.getText().toString();
                        Farmaco farmaco = global.farmaciID.get(String.valueOf(ordine.get(i).getIdFarmaco()));
                        ordinaDocumento.setText(old + "\n\nNOME: " + farmaco.getNome());
                        old = ordinaDocumento.getText().toString();
                        ordinaDocumento.setText(old + "\nPREZZO: " + farmaco.getPrezzoString(String.valueOf(ordine.get(i).getIdFarmaco())) + "€");
                        tot += Double.parseDouble(farmaco.getPrezzoString(String.valueOf(ordine.get(i).getIdFarmaco())));
                    }
                    ordinaDocumento.setText(ordinaDocumento.getText().toString() + "\n\nTOTALE: " + tot + "€");

                    ordinaDocumento.setText(ordinaDocumento.getText().toString() + "\n\nINDIRIZZO: "+ global.paziente.getIndirizzoResidenza().getCitta() + " " + global.paziente.getIndirizzoResidenza().getVia());
                } else {
                    Toast.makeText(getApplicationContext(), "Aggiungi una ricetta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<ordine.size();i++){
                    Invia(ordine.get(i));
                }
            }
        });
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void Invia(final Ricetta ricetta){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Ordine inviata", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrdinaRicettaPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("table", "5");
                params.put("idfarmacia", "1");
                params.put("idricetta", String.valueOf(ricetta.getIdRicetta()));
                if(pagato){
                    params.put("pagato", "true");
                    params.put("totale", "0");
                }else{
                    params.put("pagato", "false");
                    params.put("totale", String.valueOf(tot));
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
