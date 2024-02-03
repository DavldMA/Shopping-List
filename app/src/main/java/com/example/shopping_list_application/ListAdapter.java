package com.example.shopping_list_application;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VHLists> implements View.OnCreateContextMenuListener {
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
    public void onBindViewHolder(@NonNull VHLists holder, final int position) {
        Lists list = arLists.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("list", list);
                FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, CreateListsFragment.class, bundle)
                        .commit();
            }
        });
        holder.bind(list);
    }

    @Override
    public int getItemCount() {
        return arLists.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    public class VHLists extends RecyclerView.ViewHolder {
        TextView listName;
        public VHLists(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            //itemView.setOnCreateContextMenuListener(itemView.getContext());
        }

        public void bind(Lists list) {
            listName.setText(list.getName());
        }
    }

}
