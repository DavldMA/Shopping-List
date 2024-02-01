package com.example.shopping_list_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListsFragment extends Fragment {
    private AppDatabase db;
    RecyclerView rv;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        db = AppDatabase.getInstance(view.getContext());
        rv = view.findViewById(R.id.rvLists);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rv.setAdapter(new ListAdapter(db.listsDao().getAll()));
    }


    public ListsFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_lists, container, false);
    }


}