package com.easy.aid.Medico;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.aid.Class.NetVariables;
import com.easy.aid.MainActivity;
import com.easy.aid.R;

/**
 * @author pagina principale delle azioni di un medico
 */

public class MainMedico extends AppCompatActivity {

    ImageView back;
    private TextView nomeCognome;
    private NetVariables global;
    private LinearLayout richiesteRicette;
    private LinearLayout impostazioni;
    private LinearLayout cronologia;
    private LinearLayout calendario;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_main);

        global = (NetVariables) this.getApplication();

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

        nomeCognome = findViewById(R.id.nomeCognomeMed);
        //nomeCognome.setText((global.medico.getNome() + " " + global.medico.getCognome()));

        richiesteRicette = (LinearLayout) findViewById(R.id.richiestaRicettaMed);
        richiesteRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, RichiesteRicetteMedico.class);
                startActivity(intent);
            }
        });
        impostazioni = (LinearLayout) findViewById(R.id.impostazioniMed);
        impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, ImpostazioniMedico.class);
                startActivity(intent);
            }
        });
        cronologia = (LinearLayout) findViewById(R.id.cronologiaMed);
        cronologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, CronologiaMedico.class);
            }
        });
        calendario = (LinearLayout) findViewById(R.id.calendarioMed);
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainMedico.this, CalendarioMedico.class);
            }
        });


    }
}
