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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VHLists> {
    List<Lists> arLists;


    public ListAdapter(List<Lists> lists) {
        this.arLists = lists;
    }

    @NonNull
    @Override
    public VHLists onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VHLists vhl = new VHLists(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_layout, parent, false));
        return vhl;
    }

    @Override
    public void onBindViewHolder(@NonNull VHLists holder, @SuppressLint("RecyclerView") int position) {
       Lists list = arLists.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(v.getContext(), Details.class);
                it.putExtra("convidado", list);
                v.getContext().startActivity(it);*/
            }
        });
        holder.bind(list);
    }

    @Override
    public int getItemCount() {
        return arLists.size();
    }

    public class VHLists extends RecyclerView.ViewHolder {
        TextView listName;
        public VHLists(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.listName);
        }

        public void bind(Lists list) {
            listName.setText(list.getName());
        }
    }
}
