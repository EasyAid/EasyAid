package com.maestri.del.lavoro;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Esame extends AppCompatActivity {

    private Button avanti, indietro;
    private TextView domanda;
    private int count=0;
    private FragmentManager fragmentManager;
    private FragmentDomanda[] fragmentDomandas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esame);

        fragmentManager = getSupportFragmentManager();

        avanti = (Button) findViewById(R.id.avanti);
        indietro = (Button) findViewById(R.id.indietro);
        domanda = (TextView) findViewById(R.id.domanda);

        fragmentDomandas = new FragmentDomanda[30];

        for(int i=0;i<30;i++){
            fragmentDomandas[i] = FragmentDomanda.newInstance();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("ID", String.valueOf(count));
        FragmentDomanda fragmentDomanda = fragmentDomandas[count];
        fragmentDomanda.setArguments(bundle);
        transaction.replace(R.id.frame, fragmentDomanda, String.valueOf(count));
        transaction.commit();
        setDomanda();
        count++;

        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf((count-1)));
                if(fragment != null)
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).remove(fragment).commit();

                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);


                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(count));
                FragmentDomanda fragmentDomanda = fragmentDomandas[count];
                fragmentDomanda.setArguments(bundle);
                transaction.replace(R.id.frame, fragmentDomanda, String.valueOf(count));
                transaction.commit();
                setDomanda();
                count++;
                if(count==30){
                    startActivity(new Intent(Esame.this, Risultato.class));
                }
            }
        });

        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(count));
                if(fragment != null)
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).remove(fragment).commit();

                count--;
                setDomanda();
                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(count));
                FragmentDomanda fragmentDomanda = fragmentDomandas[count];
                fragmentDomanda.setArguments(bundle);
                transaction.replace(R.id.frame, fragmentDomanda, String.valueOf(count));
                transaction.commit();
            }
        });
    }

    private void setDomanda(){
        domanda.setText(domande[count]);
    }

    private String[] domande = new String[]{

            "Migliorare la sicurezza nei luoghi di lavoro è compito:",

            "Il servizio di prevenzione e protezione coinvolge:",

            "In un ambiente di lavoro conosciuto è bene sentirsi:",

            "Uno dei compiti del servizio di prevenzione e protezione è quello:",

            "Il preposto ha l'obbligo di:",

            "Il rappresentante dei lavoratori per la sicurezza può accedere:",

            "Il documento di valutione dei rischi è indispensabile per:",

            "Tra i rischi che il datore di lavoro deve valutare vi sondo anche quelli per:",

            "La presenza dei rischi relativi alla donna in gravidanza o al nascituro e le conseguenti misure di prevenzione devono essere comunicate a:",

            "Ogni intervento di prevenzione deve cercare di:",

            "Per i lavoratori la formazione e l'informazione in tema di sicureza e salute è:",

            "L' organizzazione e la gestione di un'evacuazione in casi di emergenza è un compito:",

            "Per rendere possibile un'evacuazione durante un'emergenza è bene controllare giornalmente:",

            "Per dare l'allarme in caso di incendio è necessario:",

            "Come si utilizza un estintore:",

            "La durata di funzionamento di un estintore è in genere di:",

            "Per evitare rischi quale operazione si deve compiere prima di utilizzare un idrante?",

            "Di fronte ad un incendio esteso e difficilmente controllabile è meglio:",

            "Durante un'evacuazione è piu' importante:",

            "Per evitare che l'incendio e il fumo si propaghino ulteriormente si deve:",

            "Qual è la prima azione da compiere quando troviamo sulla scena un infortunio:",

            "Nella richiesta di aiuto è opportuno riferire innanzitutto:",

            "Quale è la prima azione da compiere nel controllo delle condizioni dell'infortunato",

            "Se in caso di infortunio si sospetta una lesione alla colonna  vertebrale, come si deve agire:",

            "Alla base di ogni misura di prevenzione e protezione negli ambienti di lavoro c'è:",

            "Nella segnaletica di sicurezza i cartelli di colore blu servono ad indicare",

            "I dispositivi di protezione individuali si utilizzano:",

            "Il rischio di caduta in quali luoghi di lavoro è presente:",

            "Nella movimentazione di un carico è necessario che lo sforzo gravi sui muscoli:",

            "Nel lavoro al videoterminale sono da prevedere almeno:",

    };
}
