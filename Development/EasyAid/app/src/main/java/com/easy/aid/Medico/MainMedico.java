package com.easy.aid.Medico;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.easy.aid.MainActivity;
import com.easy.aid.R;

/**
 *@author
 * pagina principale delle azioni di un medico
*/

public class MainMedico extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico_main);
        back = (ImageView) findViewById(R.id.backAccessoMed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
