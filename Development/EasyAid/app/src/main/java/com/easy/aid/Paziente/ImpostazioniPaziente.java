package com.easy.aid.Paziente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.easy.aid.Class.NetVariables;
import com.easy.aid.InitialSplashScreen;
import com.easy.aid.MainActivity;
import com.easy.aid.Medico.ImpostazioniMedico;
import com.easy.aid.R;

public class ImpostazioniPaziente extends AppCompatActivity {

    private Button logout;
    private Intent intent;
    private NetVariables global;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_impostazioni);

        global = (NetVariables) this.getApplication();

        checkAPI();

        logout = findViewById(R.id.logoutPaziente);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.paziente = null;
                SharedPreferences settings = getApplicationContext().getSharedPreferences("com.easy.aid", Context.MODE_PRIVATE);
                settings.edit().clear().apply();
                intent = new Intent(ImpostazioniPaziente.this, InitialSplashScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
