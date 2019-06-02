package com.easy.aid.Farmacia;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.easy.aid.Class.Card.AdapterOrdinaFarmaco;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.R;

public class OrdinaRicetteFarmacia extends AppCompatActivity {

    private NetVariables global;

    private RecyclerView recyclerView;
    private AdapterOrdinaFarmaco adapterOrdinaFarmaco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_ordina_ricette);

        global = (NetVariables) this.getApplication();

        recyclerView = findViewById(R.id.recyclerViewFarmacia);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterOrdinaFarmaco = new AdapterOrdinaFarmaco(2, global.ricette, global.farmaciID, getApplicationContext(), null);
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
