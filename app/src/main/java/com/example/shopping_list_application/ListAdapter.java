package com.example.shopping_list_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VHConvidado> {
    List<Lists> arConvidado;


    public ListAdapter(List<Lists> convidados) {
        this.arConvidado = convidados;
    }

    @NonNull
    @Override
    public VHConvidado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VHConvidado vhc = new VHConvidado(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_layout, parent, false));
        return vhc;
    }

    @Override
    public void onBindViewHolder(@NonNull VHConvidado holder, @SuppressLint("RecyclerView") int position) {
        Lists convidado = arConvidado.get(position);
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), Details.class);
                it.putExtra("convidado", convidado);
                v.getContext().startActivity(it);
            }
        });
        holder.bind(convidado);*/
    }

    @Override
    public int getItemCount() {
        return arConvidado.size();
    }

    public class VHConvidado extends RecyclerView.ViewHolder {
        TextView listName;
        public VHConvidado(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.listName);
        }

        public void bind(Lists list) {
            //listName.setText(list.getName());
        }
    }
}
