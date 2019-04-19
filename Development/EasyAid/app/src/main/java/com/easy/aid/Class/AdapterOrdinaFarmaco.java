package com.easy.aid.Class;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private List<Ricetta> ordine;
    private Context context;

    public AdapterOrdinaFarmaco(List<Ricetta> ricette, Map<String, Farmaco> farmaci, Context context, List<Ricetta> ordine) {
        this.ricette = new ArrayList<>();
        this.ricette.addAll(ricette);
        this.farmaci = farmaci;
        this.context = context;
        this.ordine = ordine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titolo.setText(("RICETTA " + ricette.get(position).getIdRicetta()));
        int id = ricette.get(position).getIdFarmaco();
        Farmaco farmaco = farmaci.get(String.valueOf(id));
        String supp;
        supp = ("NOME<br><b>" + farmaco.getNome()+"</b>");
        holder.nome.setText(Html.fromHtml(supp));
        supp = ("DESCRIZIONE<br><b>" + farmaco.getQuatitaEusoString(String.valueOf(id))+"</b>");
        holder.descrizione.setText(Html.fromHtml(supp));
        supp = ("PREZZO: <b>" + farmaco.getPrezzoString(String.valueOf(id))+"€</b>");
        holder.prezzo.setText(Html.fromHtml(supp));

        holder.aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordine.add(ricette.get(position));
                ricette.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ricette.size());
                Toast.makeText(context, "Ordine aggiunto", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ricette.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titolo, nome, descrizione, prezzo;
        LinearLayout card;
        Button aggiungi;

        public ViewHolder(View itemView) {
            super(itemView);

            titolo = itemView.findViewById(R.id.titoloCard);
            card = itemView.findViewById(R.id.card);
            nome = itemView.findViewById(R.id.nomeCard);
            descrizione = itemView.findViewById(R.id.descrizioneCard);
            prezzo = itemView.findViewById(R.id.prezzoCard);
            aggiungi = itemView.findViewById(R.id.aggiungiCarrelloOrdinaFarmacoPaziente);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}