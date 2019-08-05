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

import com.easy.aid.R;

import java.util.List;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {


    List<Ricetta> ricette;
    Map<String, Farmaco> farmaci;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List ricette, Map farmaci) {
        this.ricette = ricette;
        this.farmaci = farmaci;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return ricette.size();
    }

    @Override
    public Object getItem(int i) {
        return ricette.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.card_view, null);

        TextView id = view.findViewById(R.id.idCardView);
        TextView farmaco = view.findViewById(R.id.farmacoCardView);
        TextView prezzo = view.findViewById(R.id.prezzoCardView);
        ImageView add = view.findViewById(R.id.addCardView);

        Farmaco f = farmaci.get(String.valueOf(ricette.get(i).getIdFarmaco()));

        id.setText(String.valueOf(ricette.get(i).getIdRicetta()));
        farmaco.setText(f.getNome());
        prezzo.setText(prezzo.getText().toString() + f.getPrezzoString(String.valueOf(ricette.get(i).getIdFarmaco())) + "€");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
