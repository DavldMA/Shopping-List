package com.example.shopping_list_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CreateListsFragment extends Fragment {

    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        EditText editTxt = (EditText) view.findViewById(R.id.etListName);
        EditText etItem = (EditText) view.findViewById(R.id.etItem);
        Button btn = (Button) view.findViewById(R.id.addBtn);
        Button saveListBtn = (Button) view.findViewById(R.id.saveListBtn);
        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(editTxt.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        saveListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

    }

    public CreateListsFragment() {
        // Required empty public constructor
    }


    public static CreateListsFragment newInstance(String param1, String param2) {
        CreateListsFragment fragment = new CreateListsFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_lists, container, false);
    }
}