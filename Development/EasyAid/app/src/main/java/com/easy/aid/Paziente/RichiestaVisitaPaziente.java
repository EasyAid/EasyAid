package com.easy.aid.Paziente;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
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

import com.easy.aid.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RichiestaVisitaPaziente extends AppCompatActivity {

    private Button continua;
    private LinearLayout datiMedico;
    private LinearLayout richiediVisita1,richiediVisita2;
    private Button inviaRichiesta;
    private TextView altroGiorno, giornoScelto, currentDate;
    private CalendarView calendar;
    private int anno,mese,giorno;
    private Boolean click=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paziente_richiesta_visita);

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

        continua = (Button) findViewById(R.id.continuaRichiestaVisitaPaz);
        datiMedico = (LinearLayout) findViewById(R.id.dataRichiediVisitaPaz);
        richiediVisita1 = (LinearLayout) findViewById(R.id.richiediVisitaPaz1);
        richiediVisita2 = (LinearLayout) findViewById(R.id.richiediVisitaPaz2);
        inviaRichiesta = (Button) findViewById(R.id.inviaRichiestaVisitaPaz);
        altroGiorno = (TextView) findViewById(R.id.selezionaAltroGiornoPaz);
        currentDate = (TextView) findViewById(R.id.currentDateRichiestaPaziente);
        giornoScelto = (TextView) findViewById(R.id.giornoRichiediVisitaPaz);
        calendar = (CalendarView) findViewById(R.id.calendarioRichiediVisitaPaz);

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
                Toast.makeText(getApplicationContext(), "Richiesta inoltrata", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
