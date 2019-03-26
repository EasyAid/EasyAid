package com.easy.aid;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.easy.aid.Class.NetVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InitialSplashScreen extends AppCompatActivity {

    private NetVariables c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        c = ((NetVariables) this.getApplication());

        c.nomeFarmaci = new ArrayList<>();
        c.usoFarmaci = new ArrayList<>();
        c.prezzoFarmaci = new ArrayList<>();

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

                c.nomeFarmaci.add(nome);
                c.usoFarmaci.add(usoQuantita);
                c.prezzoFarmaci.add(prezzo);

            }

            Toast.makeText(this, "finito", Toast.LENGTH_SHORT).show();

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
