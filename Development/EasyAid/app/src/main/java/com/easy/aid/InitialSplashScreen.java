package com.easy.aid;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.easy.aid.Class.Farmaco;
import com.easy.aid.Class.NetVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InitialSplashScreen extends AppCompatActivity {

    private NetVariables c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //CONTROLLA LE API DEL TELEFONO, SE MAGGIORI DI MARSHMELLOW MODIFICA IL COLORE DEL TESTO DELLA NOTIFICATION BAR IN CHIARO
        //ALTRIMENTI SE E' INFERIORE ALLE API 23 MODIFICA LA NOTIFICATION BAR IN COLORE SCURO (IN QUANTO NON PUO MODIFICARE IL COLORE DEL TESTO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(getApplicationContext(),R.color.colorAccent));
        }

        c = ((NetVariables) this.getApplication());
        c.farmaci = new HashMap<>();
        c.province = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("elenco_farmaci.CSV"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] riga = mLine.split(";");

                String nome = riga[3];
                String usoQuantita = riga[1];
                riga[4] = riga[4].replace(",",".");
                String prezzo = riga[4];

                if(c.farmaci.containsKey(nome)){
                    c.farmaci.get(nome).setQuatitaEuso(usoQuantita);
                    c.farmaci.get(nome).setPrezzo(prezzo);
                }else{
                    c.farmaci.put(nome,new Farmaco(nome,usoQuantita,prezzo));
                }
            }

            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("province.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading

            while ((mLine = reader.readLine()) != null) {

                String provincia = mLine;
                c.province.add(provincia);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                startActivity(new Intent(InitialSplashScreen.this, MainActivity.class));
                finish();
            }

        }.start();
    }
}
