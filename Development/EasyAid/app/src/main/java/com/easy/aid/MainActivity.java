package com.easy.aid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.aid.Farmacia.AccessoFarmacia;
import com.easy.aid.Medico.AccessoMedico;
import com.easy.aid.Paziente.AccessoPaziente;
import com.easy.aid.Paziente.MainPaziente;


public class MainActivity extends AppCompatActivity {

    private LinearLayout sceltaMedico;
    private LinearLayout sceltaPaziente;
    private LinearLayout sceltaFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sceltaMedico = findViewById(R.id.sceltaMedico);
        sceltaPaziente = findViewById(R.id.sceltaPaziente);
        sceltaFarmacia = findViewById(R.id.sceltaFarmacia);

        sceltaPaziente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AccessoPaziente.class);
                startActivity(i);
                finish();
            }
        });

        sceltaMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AccessoMedico.class);
                startActivity(i);
                finish();
            }
        });

        sceltaFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AccessoFarmacia.class);
                startActivity(i);
                finish();
            }
        });


    }
}
