package com.example.shopping_list_application;

import static java.lang.Boolean.parseBoolean;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;

    private int id_1;
    private int id_2;

    public CustomAdapter(Context context,Integer id1, Integer id2, ArrayList<String> list1, ArrayList<String> list2) {
        super(context, 0, list1);
        arrayList1 = list1;
        arrayList2 = list2;
        id_1 = id1;
        id_2 = id2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }
        return convertView;
    }
}