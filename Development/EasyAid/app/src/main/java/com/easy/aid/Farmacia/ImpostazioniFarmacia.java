package com.easy.aid.Farmacia;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.easy.aid.R;

public class ImpostazioniFarmacia extends AppCompatActivity {

    private ScrollView pag1;
    private LinearLayout pag2;

    private EditText email, telefono, provincia, citta, via;
    private EditText oldPsw, newPsw, confPsw;

    private Button footer;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_impostazioni);

        checkAPI();

        pag1 = findViewById(R.id.primaPagImpostazioniFarm);
        pag2 = findViewById(R.id.secondaPagImpostazioniFarm);

        email = findViewById(R.id.emailImpostazioniFarm);
        telefono = findViewById(R.id.telefonoImpostazioniFarm);

        provincia = findViewById(R.id.provinciaImpostazioniFarm);
        citta = findViewById(R.id.cittaImpostazioniFarm);
        via = findViewById(R.id.viaImpostazioniFarm);

        oldPsw = findViewById(R.id.vecchiaPasswordImpostazioniFarm);
        newPsw = findViewById(R.id.nuovaPasswordImpostazioniFarm);
        confPsw = findViewById(R.id.confermaPasswordImpostazioniFarm);

        footer = findViewById(R.id.footerImpostazioniFarm);
        status = 0;

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 0: {
                        //TODO prima pagina -> se nulla vuoto invia dati per aggiornarli
                    }
                    break;
                    case 1: {
                        //TODO modifica password -> se nulla vuoto, se password vecchia giusta e nuove password corrispondono invia dati password per aggiornare
                    }
                }
            }
        });
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