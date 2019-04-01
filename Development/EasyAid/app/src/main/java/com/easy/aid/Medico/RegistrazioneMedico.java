package com.easy.aid.Medico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.easy.aid.R;

public class RegistrazioneMedico extends AppCompatActivity {

    private ScrollView registrazione1, registrazione2, registrazione3;
    private Button continua1;
    private ImageView back;
    private int status;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_registrazione);

        back = (ImageView) findViewById(R.id.backRegistrazioneMed);
        registrazione1 = (ScrollView) findViewById(R.id.registrazione1Med);
        registrazione2 = (ScrollView) findViewById(R.id.registrazione2Med);
        registrazione3 = (ScrollView) findViewById(R.id.registrazione3Med);
        continua1 = (Button) findViewById(R.id.continuaMed);
        status = 0;


        continua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case 0: {
                        registrazione1.setVisibility(View.GONE);
                        registrazione2.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        registrazione2.setVisibility(View.GONE);
                        registrazione3.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        // TODO: 01/04/2019 Controllare se ha compilato tutti campi
                        intent = new Intent(RegistrazioneMedico.this, MainMedico.class);
                        startActivity(intent);
                        break;
                    }
                }

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 0: {
                        intent = new Intent(RegistrazioneMedico.this, AccessoMedico.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        registrazione2.setVisibility(View.GONE);
                        registrazione1.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        registrazione3.setVisibility(View.GONE);
                        registrazione2.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }
}
