package com.easy.aid.Paziente;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.InitialSplashScreen;
import com.easy.aid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RichiediVisitaPaziente extends AppCompatActivity {

    private Button continua;
    private LinearLayout datiMedico;
    private LinearLayout richiediVisita1,richiediVisita2;
    private Button inviaRichiesta;
    private TextView altroGiorno, giornoScelto, currentDate;
    private TextView nomePaziente, nomeMedico;
    private CalendarView calendar;
    private int anno,mese,giorno;
    private Boolean click=false;
    private NetVariables global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiedi_visita);

        checkAPI();

        global = (NetVariables) this.getApplication();

        continua = findViewById(R.id.continuaRichiestaVisitaPaz);
        datiMedico = findViewById(R.id.dataRichiediVisitaPaz);
        richiediVisita1 = findViewById(R.id.richiediVisitaPaz1);
        richiediVisita2 = findViewById(R.id.richiediVisitaPaz2);
        inviaRichiesta = findViewById(R.id.inviaRichiestaVisitaPaz);
        altroGiorno = findViewById(R.id.selezionaAltroGiornoPaz);
        currentDate = findViewById(R.id.currentDateRichiestaPaziente);
        giornoScelto = findViewById(R.id.giornoRichiediVisitaPaz);
        calendar = findViewById(R.id.calendarioRichiediVisitaPaz);
        nomeMedico = findViewById(R.id.nomeCognomeMedicoRichiediVisitaPaziente);
        nomePaziente = findViewById(R.id.nomeCognomePazienteRichiediVisitaPaziente);

        nomePaziente.setText((nomePaziente.getText().toString()+ " " + global.paziente.getNome().toUpperCase()+ " "+ global.paziente.getCognome().toUpperCase()));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String supp =  formatter.format(new Date());
        currentDate.setText(supp);

        calendar.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                anno = year;
                mese = month;
                giorno = dayOfMonth;

                //todo controlla se la data non sia una domenica o prima di oggi
                String sup;
                if(mese+1<=9){
                    sup =  "GIORNO: " + giorno +"/0" + (mese+1) +"/" + anno;
                }else{
                    sup =  "GIORNO: " + giorno +"/" + (mese+1) +"/" + anno;
                }
                giornoScelto.setText(sup);
                click=true;
            }
        });

        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!click){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String supp =  "GIORNO: " + formatter.format(new Date());
                    giornoScelto.setText(supp);
                }

                datiMedico.setVisibility(View.VISIBLE);
                richiediVisita1.setVisibility(View.GONE);
                richiediVisita2.setVisibility(View.VISIBLE);

            }
        });

        altroGiorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datiMedico.setVisibility(View.GONE);
                richiediVisita1.setVisibility(View.VISIBLE);
                richiediVisita2.setVisibility(View.GONE);
            }
        });

        inviaRichiesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invia();
            }
        });
    }

    private void Invia() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetVariables.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Richiesta inviata", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RichiediVisitaPaziente.this, "Error " + error.toString() , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //todo migliora
                params.put("table", "4");
                params.put("idpaziente", String.valueOf(global.paziente.getID()));
                params.put("idmedico", "2");
                params.put("statorichiesta", "IN ATTESA");
                params.put("data", "2019-04-19");
                params.put("ora", "11:11");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


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
