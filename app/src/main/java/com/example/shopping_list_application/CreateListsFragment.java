package com.example.shopping_list_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CreateListsFragment extends Fragment {

    private AppDatabase db;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        db = AppDatabase.getInstance(view.getContext());

        EditText editTxt = (EditText) view.findViewById(R.id.etListName);
        EditText etItem = (EditText) view.findViewById(R.id.etItem);
        Button btn = (Button) view.findViewById(R.id.addBtn);
        Button saveListBtn = (Button) view.findViewById(R.id.saveListBtn);
        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<String>();


        if(!requireArguments().isEmpty()) {
            Bundle receivedArgs = requireArguments();
            Lists selectedList = receivedArgs.getParcelable("list");
            editTxt.setText(selectedList.getName());
            if(!selectedList.getItems().isEmpty())
                for(String item : selectedList.getItems())
                    arrayList.add(item);
        }








        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etItem.getText().toString().isEmpty()) {
                    arrayList.add(etItem.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        saveListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!requireArguments().isEmpty()) {
                    Bundle receivedArgs = requireArguments();
                    Lists selectedList = receivedArgs.getParcelable("list");
                    selectedList.setItems(arrayList);
                    db.listsDao().update(selectedList);
                }
                else {
                    Lists list = new Lists(editTxt.getText().toString(), arrayList);
                    db.listsDao().insert(list);
                }
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etItem.setText(((TextView) view).getText());
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
        Log.i("a",""+savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_lists, container, false);
    }
}