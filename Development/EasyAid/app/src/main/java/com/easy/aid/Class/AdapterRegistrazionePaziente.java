package com.easy.aid.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.aid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterRegistrazionePaziente extends RecyclerView.Adapter<AdapterRegistrazionePaziente.ViewHolder> implements TextWatcher {


    private List<Integer> id;
    private List<String> nomiMedici;
    private List<String> studiMedici;

    public AdapterRegistrazionePaziente(List<Integer> id, List<String> nomiMedici, List<String> studiMedici) {
        this.id = id;
        this.nomiMedici = nomiMedici;
        this.studiMedici = studiMedici;
    }

    @NonNull
    @Override
    public AdapterRegistrazionePaziente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medico, parent, false);
        return new AdapterRegistrazionePaziente.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.nomeCognome.setText(nomiMedici.get(position));
        holder.studio.setText(studiMedici.get(position));

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomeCognome, studio;
        LinearLayout main;

        public ViewHolder(View itemView) {
            super(itemView);

            nomeCognome = itemView.findViewById(R.id.nomeCognomeCardMedico);
            studio = itemView.findViewById(R.id.studioCardMedico);
            main = itemView.findViewById(R.id.main);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
