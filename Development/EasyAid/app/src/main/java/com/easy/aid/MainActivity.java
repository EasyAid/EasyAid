package com.easy.aid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.aid.Class.NetVariables;
import com.easy.aid.Farmacia.AccessoFarmacia;
import com.easy.aid.Medico.AccessoMedico;
import com.easy.aid.Medico.MainMedico;
import com.easy.aid.Paziente.AccessoPaziente;
import com.easy.aid.Paziente.MainPaziente;


public class MainActivity extends AppCompatActivity {

    private LinearLayout sceltaMedico;
    private LinearLayout sceltaPaziente;
    private LinearLayout sceltaFarmacia;
    private NetVariables global;
    private NetVariables netVariables;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RESTA CONNESSO
        netVariables = (NetVariables) this.getApplication();

        netVariables.prefs = this.getSharedPreferences(
                "com.easy.aid.Paziente", Context.MODE_PRIVATE);

        if(netVariables.prefs.getString("CF", null) != null){

            if(netVariables.prefs.getString("settore", null).equals("Paziente")){
                intent = new Intent(MainActivity.this, MainPaziente.class);

            }else if(netVariables.prefs.getString("settore", null).equals("Medico")){
                intent = new Intent(MainActivity.this, MainMedico.class);

            }else if(netVariables.prefs.getString("settore", null).equals("Farmacia")){

            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("CF", netVariables.prefs.getString("CF", null));
            startActivity(intent);
            finish();
        }


        setContentView(R.layout.activity_main);
        global = (NetVariables)this.getApplication();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(),R.color.colorAccent));
        }

        sceltaMedico = findViewById(R.id.sceltaMedico);
        sceltaPaziente = findViewById(R.id.sceltaPaziente);
        sceltaFarmacia = findViewById(R.id.sceltaFarmacia);

        sceltaPaziente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global.checktime()) {
                    return;
                }
                Intent i = new Intent(MainActivity.this, AccessoPaziente.class);
                startActivity(i);
            }
        });

        sceltaMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global.checktime()) {
                    return;
                }
                Intent i = new Intent(MainActivity.this, AccessoMedico.class);
                startActivity(i);
            }
        });

        sceltaFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global.checktime()) {
                    return;
                }
                Intent i = new Intent(MainActivity.this, AccessoFarmacia.class);
                startActivity(i);
            }
        });
    }

}
