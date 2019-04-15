package com.maestri.del.lavoro;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FragmentDomanda extends Fragment {

    private String ID;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3;

    public static FragmentDomanda newInstance() {

        FragmentDomanda myFragment = new FragmentDomanda();

        return myFragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        ID = getArguments().getString("ID");
        return inflater.inflate(R.layout.fragment_domanda, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        radioButton1 = (RadioButton) view.findViewById(R.id.radio1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radio2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radio3);

        radioButton1.setText(risposte1[Integer.parseInt(ID)]);
        radioButton2.setText(risposte2[Integer.parseInt(ID)]);
        radioButton3.setText(risposte3[Integer.parseInt(ID)]);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(radioGroup.getCheckedRadioButtonId()==radioButton1.getId()){
                    publicVariables.risposte[Integer.parseInt(ID)] = 1;

                }else if(radioGroup.getCheckedRadioButtonId()==radioButton2.getId()){
                    publicVariables.risposte[Integer.parseInt(ID)] = 2;

                }else if(radioGroup.getCheckedRadioButtonId()==radioButton3.getId()){
                    publicVariables.risposte[Integer.parseInt(ID)] = 3;
                }
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private String[] risposte1 = new String[]{

            "Di tutti, dal dirigente al lavoratore",

            "Datore di lavoro, medico competente e parte dei lavoratori",

            "Comunque esposti alla possibilità di incidenti",

            "Rappresentante dei lavoratori",

            "Controllare che i lavoratori applichino le misure di sicurezza aziendali",

            "Alla documentazione sanitaria del lavoratore",

            "Programmare gli interventi di prevenzione e protezione",

            "La salute riproduttiva dell'uomo",

            "Tutti i lavoratori",

            "Ridurre i rischi alla fonte",

            "Un diritto e un dovere",

            "Del rappresentante dei lavoratori per la sicurezza",

            "La visibilità degli estintori",

            "Premere il pulsante di allarme incendio",

            "Rimuovendo la sicura e dirigendo il getto a ventaglio sulle fiamme",

            "Un minuto",

            "Interrompere l'alimentazione elettrica",

            "Attendere gli addetti del servizio antincendio evitando rischi",

            "Mantenere la calma e aiutare chi è in difficoltà",

            "Non chiudere le porte dei locali da cui si esce",

            "Cercare di curare l'infortunato",

            "La gravità degli infortunati",

            "Verificare lo stato di coscienza ",

            "Girare l'infortunato in posizione utile per controllare il respiro",

            "La sicurezza",

            "Un pericolo",

            "Quando non sono di intralcio nelle attività",

            "Nei cantieri edili",

            "Delle gambe",

            "5 minuti di pausa ogni ora di lavoro continuo",
    };

    private String[] risposte2 = new String[]{

            "Dei Vigili del Fuoco",

            "Consulenti esterni e rappresentanti dei lavoratori",

            "Esposti a incidenti solo nelle attività pericolose",

            "Intervenire nel caso di incidenti e/o emergenze",

            "Segnalare al datore di lavoro gli interventi per ridurre i rischi",

            "Ai luoghi del lavoro che gli sono stati assegnati",

            "Gestire le emergenze sanitarie",

            "La salute del neonato",

            "Ai lavoratori",

            "Eliminare o, se non è possibile, ridurre i rischi alla fonte",

            "Un dovere secondo l'attività svolta",

            "Degli addetti al Servizio Sicurezza",

            "Lo stato dei dispositivi individuale di protezione",

            "Dare l'allarme seguendo le procedure di emergenza",

            "Posizionandosi con il vento alle spalle del fuoco ",

            "Qualche minuto",

            "Distendere interamente la manichetta",

            "Utilizzare almeno due estintori contemporaneamente",

            "Dirigersi il piu' velocemente possibile alle vie di fuga",

            "Aprire porte e finestre per fare entrare aria",

            "Non compiere nessuna azione per evitare di aggravare la situazione",

            "Il numero di telefono da cui si sta chiamando",

            "Verificare la presenza del battito cardiaco",

            "Spostarlo lentamente tendendo bloccata la colonna vertebrale",

            "L'ordine e la pulizia di tutti gli ambienti",

            "Un divieto",

            "Quando i rischi non possono essere evitati in altro modo",

            "Sulle impalcature oltre i 5 metri di altezza",

            "Delle braccia",

            "10 minuti di pausa ogni ora di lavoro continuo",
    };

    private String[] risposte3 = new String[]{

            "Del datore di lavoro",

            "Datore di lavoro e consulenti esterni",

            "Sempre al sicuro da qualunque incidente",

            "Preparare il documento di valutazione dei rischi",

            "Individuare i dispositivi di protezione individuali piu efficaci",

            "Alla documentazione aziendale relativa alla sicurezza e alla salute, inclusa copia del D.V.R.",

            "Definire gli incarichi di lavoro",

            "La salute della donna in gravidanza e del nascituro",

            "Alle lavoratrici",

            "Eliminare i rischi alla fonte",

            "Una scelta facoltativa",

            "Degli addetti al Servizio Antincendio e Primo Soccorso",

            "La percorribilità delle vie di fuga",

            "Telefonare alla squadra di emergenza",

            "Rimuovendo la sicura e digirendo il getto alla base delle fiamme",

            "10-15 secondi",

            "Aprire il circuito idraulico",

            "Attaccare le fiamme da piu' lati ",

            "Utilizzare gli ascensori ed evitare le scale",

            "Chiudere le porte dei locali da cui si esce",

            "Chiamare i soccorsi",

            "Il luogo dell'infortunio e la gravità degli infortunati",

            "Verificare la presenza di respiro",

            "Non spostarlo per non provocare ulteriori lesioni alla colonna vertebrale",

            "L'ordine e la pulizia delle vie di fuga",

            "Un obbligo",

            "Quando richiesti dal datore di lavoro",

            "In tutti i luoghi di lavoro",

            "Della schiena",

            "15 minuti di pausa ogni ora di lavoro continuo",
    };
}
