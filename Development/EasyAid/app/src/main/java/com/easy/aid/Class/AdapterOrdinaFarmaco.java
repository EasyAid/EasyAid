package com.easy.aid.Class;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.aid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterOrdinaFarmaco extends RecyclerView.Adapter<AdapterOrdinaFarmaco.ViewHolder> {


    private List<Ricetta> ricette;
    private Map<String, Farmaco> farmaci;

    public AdapterOrdinaFarmaco(List<Ricetta> ricette, Map<String, Farmaco> farmaci) {
        this.ricette = ricette;
        this.farmaci = farmaci;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titolo.setText(("RICETTA " + ricette.get(position).getIdRicetta()));
    }

    @Override
    public int getItemCount() {
        return ricette.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titolo, nome, descrizione, prezzo;
        LinearLayout card;

        public ViewHolder(View itemView) {
            super(itemView);

            titolo = itemView.findViewById(R.id.titoloCard);
            card = itemView.findViewById(R.id.card);
            descrizione = itemView.findViewById(R.id.descrizioneCard);
            prezzo = itemView.findViewById(R.id.prezzoCard);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}