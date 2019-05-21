package com.easy.aid.Farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.easy.aid.MainActivity;
import com.easy.aid.R;

public class MainFarmacia extends AppCompatActivity {

    private LinearLayout video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmacia_main);

        video = findViewById(R.id.richiestaFarmacoFarm);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFarmacia.this, OrdinaRicetteFarmacia.class));
            }
        });
    }
}
