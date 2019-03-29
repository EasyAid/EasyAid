package com.easy.aid.Medico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.easy.aid.R;

public class RegistrazioneMedico extends AppCompatActivity {

    private RelativeLayout registrazione1, registrazione2;
    private Button continua1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_registrazione);


        registrazione1 = (RelativeLayout) findViewById(R.id.registrazione1Med);
        registrazione2 = (RelativeLayout) findViewById(R.id.registrazione1Med);
        continua1 = (Button) findViewById(R.id.continua1Med);

        continua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrazione1.setVisibility(View.GONE);
                registrazione2.setVisibility(View.VISIBLE);
            }
        });

    }
}
