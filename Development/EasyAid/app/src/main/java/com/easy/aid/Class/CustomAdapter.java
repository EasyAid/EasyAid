package com.easy.aid.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.aid.Paziente.OrdinaRicettaPaziente;
import com.easy.aid.R;

import java.util.List;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {

    List<Ricetta> ordine;
    List<Ricetta> ricette;
    Map<String, Farmaco> farmaci;
    Map<String, Farmaco> farmaciID;
    LayoutInflater inflter;
    Context context;
    Boolean stato;
    TextView totale;

    private ImageView add, remove;

    public CustomAdapter(Context applicationContext, List ricette, Map farmaci, List ordine, Boolean stato, TextView totale, Map farmaciID) {
        this.ricette = ricette;
        this.farmaci = farmaci;
        this.farmaciID = farmaciID;
        this.ordine = ordine;
        context = applicationContext;
        this.stato = stato;
        this.totale = totale;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        if(stato)
            return ricette.size();
        else
            return ordine.size();

    }

    @Override
    public Object getItem(int i) {
        if(stato)
            return ricette.get(i);
        else
            return ordine.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.card_view, null);

        TextView farmaco = view.findViewById(R.id.farmacoCardView);
        TextView prezzo = view.findViewById(R.id.prezzoCardView);
        add = view.findViewById(R.id.addCardView);
        remove = view.findViewById(R.id.removeCardView);

        Farmaco f;
        add.setTag(i);
        remove.setTag(i);

        if(stato){
            f = farmaci.get(String.valueOf(ricette.get(i).getIdFarmaco()));
            farmaco.setText(f.getNome());
            prezzo.setText((prezzo.getText().toString() + f.getPrezzoString(String.valueOf(ricette.get(i).getIdFarmaco())) + "€"));
            remove.setVisibility(View.GONE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int)v.getTag();

                    ordine.add(ricette.get(pos));
                    ricette.remove(ricette.get(pos));
                    notifyDataSetChanged();

                    Toast.makeText(context, "Ricetta aggiunta al carrello", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            f = farmaci.get(String.valueOf(ordine.get(i).getIdFarmaco()));
            farmaco.setText(f.getNome());
            prezzo.setText((prezzo.getText().toString() + f.getPrezzoString(String.valueOf(ordine.get(i).getIdFarmaco())) + "€"));
            add.setVisibility(View.GONE);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int)v.getTag();

                    ricette.add(ordine.get(pos));
                    ordine.remove(ordine.get(pos));
                    notifyDataSetChanged();
                    double tot=0;
                    for(int i=0;i<ordine.size();i++){
                        Farmaco f = farmaciID.get(String.valueOf(ordine.get(i).getIdFarmaco()));
                        Double prezzo = Double.valueOf(f.getPrezzoString(String.valueOf(ordine.get(i).getIdFarmaco())));
                        tot += prezzo;
                    }
                    totale.setText("TOTALE: " + tot + "€");

                    Toast.makeText(context, "Ricetta rimossa dal carrello", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
