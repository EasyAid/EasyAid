package com.easy.aid.Class;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.aid.Paziente.OrdinaRicettaPaziente;
import com.easy.aid.R;

import java.util.List;
import java.util.Map;

public class CronoAdapter extends BaseAdapter {

    LayoutInflater inflter;
    List<Ricetta> ricetta;
    List<Ordine> ordine;
    boolean stato;
    Context context;
    NetVariables global;

    private ImageView add, remove;

    public CronoAdapter(Context applicationContext, boolean stato, List ricetta, List ordine, NetVariables global) {
        this.global = global;
        this.stato = stato;
        this.ricetta = ricetta;
        this.ordine = ordine;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        if(stato)
            return ricetta.size();
        else
            return ordine.size();
    }

    @Override
    public Object getItem(int i) {
        if(stato)
            return ricetta.get(i);
        else
            return ordine.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.card_view_cronologia, null);

        LinearLayout ricetteLinear = view.findViewById(R.id.ricetteCardCrono);
        LinearLayout ordiniLinear = view.findViewById(R.id.ordiniCardCrono);

        if(stato){
            ordiniLinear.setVisibility(View.GONE);
            TextView farmaco = view.findViewById(R.id.ricetteFarmacoCard);
            TextView data = view.findViewById(R.id.ricetteDataCard);
            TextView stato = view.findViewById(R.id.statoCard);

            Farmaco f = global.farmaciID.get(String.valueOf(ricetta.get(i).getIdFarmaco()));

            farmaco.setText(f.getNome());
            data.setText((data.getText().toString() + ricetta.get(i).getData()));
            stato.setText((stato.getText().toString() + "\n" + ricetta.get(i).getStatoRichiesta()));
        }else{
            ricetteLinear.setVisibility(View.GONE);

            TextView data = view.findViewById(R.id.dataOrdineCardCrono);
            TextView totale = view.findViewById(R.id.totaleCardCrono);

            TextView nome_prezzo = view.findViewById(R.id.nome_prezzoFarmacoCardCrono);

            data.setText((data.getText().toString() + ordine.get(i).getData()));
            totale.setText((totale.getText().toString() + String.valueOf(ordine.get(i).getTotale()) + "€"));

            for (int c=0;c<ordine.get(i).getIdRicetta().size();c++){
                int IdRicetta = ordine.get(i).getIdRicetta().get(c);
                int IdFarmaco = global.ricetteORDINATE.get(String.valueOf(IdRicetta)).getIdFarmaco();
                String nomeF = global.farmaciID.get(String.valueOf(IdFarmaco)).getNome();
                String prezzoF = global.farmaciID.get(String.valueOf(IdFarmaco)).getPrezzoString(String.valueOf(IdFarmaco));

                nome_prezzo.setText((nome_prezzo.getText().toString() + "\n" + ("FARMACO:" + "\n" + nomeF)));
                nome_prezzo.setText((nome_prezzo.getText().toString() + "\n" + ("PREZZO: " + prezzoF + "€") + "\n"));
            }

        }

        return view;
    }
}
