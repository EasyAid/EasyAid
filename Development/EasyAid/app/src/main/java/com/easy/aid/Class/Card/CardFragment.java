package com.easy.aid.Class.Card;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.aid.Class.Farmaco;
import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;
import com.easy.aid.R;

import java.util.List;

public class CardFragment extends Fragment {

    private static NetVariables global;
    private CardView cardView;
    private static List<Ricetta> ordine;
    private static List<CardFragment> fragments;
    private static CardFragmentPagerAdapter thi;

    public static Fragment getInstance(int position, NetVariables g, List<Ricetta> o, List<CardFragment> fr, CardFragmentPagerAdapter th) {
        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        global = g;
        ordine = o;
        fragments = fr;
        thi = th;

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_view, container, false);
        final int position = fragments.indexOf(this);

        cardView = (CardView) view.findViewById(R.id.text);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        LinearLayout accettaRifiuta = view.findViewById(R.id.accettarifiutaCard);
        Button aggiungi = view.findViewById(R.id.aggiungiCarrelloOrdinaFarmacoPaziente);
        TextView nomePaziente = view.findViewById(R.id.pazienteCard);
        TextView nomeFarmaco = view.findViewById(R.id.nomeCard);
        TextView descrizione = view.findViewById(R.id.descrizioneCard);
        TextView prezzo = view.findViewById(R.id.prezzoCard);

        nomePaziente.setVisibility(View.GONE);
        accettaRifiuta.setVisibility(View.GONE);

        final Ricetta R = global.ricette.get(position);
        String idF = String.valueOf(global.ricette.get(position).getIdFarmaco());
        Farmaco F = global.farmaciID.get(idF);

        String nomeF = F.getNome();
        nomeFarmaco.setText((nomeFarmaco.getText().toString() + "\n" + nomeF));
        String prezzoF = F.getPrezzoString(idF);
        prezzo.setText((prezzo.getText().toString() + prezzoF + "€"));
        String descrizioneF = R.getDescrizione();
        descrizione.setText((descrizione.getText().toString() + "\n" + descrizioneF));

        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordine.add(R);
                thi.deletePage(position);
            }
        });


        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
}
