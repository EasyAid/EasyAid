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
    private int utente;

    public AdapterOrdinaFarmaco(int id, List<Ricetta> ricette, Map<String, Farmaco> farmaci, Context context, List<Ricetta> ordine) {
        this.utente = id;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.titolo.setText(("RICETTA " + ricette.get(position).getIdRicetta()));
        int id = ricette.get(position).getIdFarmaco();
        Farmaco farmaco = farmaci.get(String.valueOf(id));
        String supp;
        supp = ("NOME<br><b>" + farmaco.getNome()+"</b>");
        holder.nome.setText(Html.fromHtml(supp));

        if(utente==0){

            holder.accettarifiuta.setVisibility(View.GONE);
            holder.paziente.setVisibility(View.GONE);

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
        }else if(utente == 1){
            supp = ("PAZIENTE<br><b>ROSSI MARIO</b>");
            holder.paziente.setText(Html.fromHtml(supp));
            holder.descrizione.setText((holder.descrizione.getText().toString() + "\n" +ricette.get(position).getDescrizione()));
            holder.prezzo.setVisibility(View.GONE);
            holder.aggiungi.setVisibility(View.GONE);
            holder.accetta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ricette.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, ricette.size());
                    Toast.makeText(context, "Richiesta accettata", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(utente == 2){
            supp = ("CLIENTE<br><b>ROSSI MARIO</b>");
            holder.paziente.setText(Html.fromHtml(supp));
            holder.descrizione.setText("LOCALITA<br><b>VIALE EUROPA N°3 (BG)</b>");
            holder.prezzo.setText("PREZZO<br><b>2,51€</b>");
            holder.aggiungi.setVisibility(View.GONE);
            holder.accetta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ricette.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, ricette.size());
                    Toast.makeText(context, "Richiesta accettata", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return ricette.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titolo, nome, descrizione, prezzo, paziente;
        LinearLayout card, accettarifiuta;
        Button aggiungi, accetta;

        public ViewHolder(View itemView) {
            super(itemView);

            titolo = itemView.findViewById(R.id.titoloCard);
            card = itemView.findViewById(R.id.card);
            accetta = itemView.findViewById(R.id.accettaCard);
            paziente = itemView.findViewById(R.id.pazienteCard);
            nome = itemView.findViewById(R.id.nomeCard);
            descrizione = itemView.findViewById(R.id.descrizioneCard);
            prezzo = itemView.findViewById(R.id.prezzoCard);
            aggiungi = itemView.findViewById(R.id.aggiungiCarrelloOrdinaFarmacoPaziente);
            accettarifiuta = itemView.findViewById(R.id.accettarifiutaCard);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}