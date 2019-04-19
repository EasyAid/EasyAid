package com.easy.aid.Medico;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.Paziente.MainPaziente;
import com.easy.aid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RichiesteRicetteMedico extends AppCompatActivity {

    private NetVariables global;

    private RecyclerView recyclerView;
    private AdapterOrdinaFarmaco adapterOrdinaFarmaco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_richieste_ricette);

        global = (NetVariables) this.getApplication();

        recyclerView = findViewById(R.id.recyclerViewMedico);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterOrdinaFarmaco = new AdapterOrdinaFarmaco(1, global.ricette, global.farmaciID, getApplicationContext(), null);
        recyclerView.setAdapter(adapterOrdinaFarmaco);

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
