package com.example.shopping_list_application;

import static java.lang.Boolean.parseBoolean;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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


    ListView listViewData;
    ArrayAdapter<String> adapter;
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    int clickCounter=0;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        db = AppDatabase.getInstance(view.getContext());
        EditText editTxt = (EditText) view.findViewById(R.id.etListName);
        EditText etItem = (EditText) view.findViewById(R.id.etItem);
        Button btn = (Button) view.findViewById(R.id.addBtn);
        Button saveListBtn = (Button) view.findViewById(R.id.saveListBtn);
        listViewData = view.findViewById(R.id.lv_data);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayListDone = new ArrayList<String>();

        if(!requireArguments().isEmpty()) {
            editTxt.setEnabled(false);
            Bundle receivedArgs = requireArguments();
            Lists selectedList = receivedArgs.getParcelable("list");
            editTxt.setText(selectedList.getName());
            if(selectedList.getItems() != null && !selectedList.getItems().isEmpty())
                for(String item : selectedList.getItems())
                    arrayList.add(item);

            if(selectedList.getIsDonned() != null && !selectedList.getIsDonned().isEmpty() && selectedList.getIsDonned().size() >0) {
                for(String item : selectedList.getIsDonned()) {
                    arrayListDone.add(item);
                }
            }
            else{
                selectedList.setIsDonned(new ArrayList<String>());
                db.listsDao().update(selectedList);
            }
        }

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
                    selectedList.setIsDonned(arrayListDone);
                    db.listsDao().update(selectedList);
/*
                    if(isNetworkAvailable(view)){
                        JSONObject postData = new JSONObject();
                        Gson gson = new GsonBuilder().create();
                        try {
                            postData.put("username", db.userDao().loadUser().getUsername());
                            postData.put("list", gson.toJson(selectedList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        APIRequests.PostData("https://shopping-list-api-beta.vercel.app/list/add",view.getContext(), postData, new APIRequests.ApiListener() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("POST Request", "Success: " + response.toString());
                            }
                            @Override
                            public void onError(String error) {
                                Log.e("POST Request", "Error: " + error);
                            }
                        });
                    }*/
                }
                else {
                    Lists list = new Lists(editTxt.getText().toString(), arrayList, arrayListDone);
                    db.listsDao().insert(list);

                    if(isNetworkAvailable(view)){
                        JSONObject postData = new JSONObject();
                        Gson gson = new GsonBuilder().create();
                        try {
                            postData.put("username", db.userDao().loadUser().getUsername());
                            postData.put("list", gson.toJson(list));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        APIRequests.PostData("https://shopping-list-api-beta.vercel.app/list/add",view.getContext(), postData, new APIRequests.ApiListener() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {
                                Log.d("POST Request", "Success: " + response.toString());
                            }
                            @Override
                            public void onError(String error) {
                                Log.e("POST Request", "Error: " + error);
                            }
                        });
                    }
                }
            }
        });

        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_item_layout, R.id.checkTextView, arrayList) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Button buttonDelete = view.findViewById(R.id.buttonDelete);
                CheckedTextView textView2 = view.findViewById(R.id.checkTextView);
                if(!arrayListDone.isEmpty() && position < arrayListDone.size()) {
                    if(arrayListDone.get(position) != null) {
                        textView2.setChecked(Boolean.parseBoolean(arrayListDone.get(position)));
                    }
                }
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                return view;
            }
        };
        listViewData.setAdapter(adapter);

        listViewData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checkTextView);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                arrayListDone.add(position,""+checkedTextView.isChecked());
                Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "" + checkedTextView.isChecked());
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
    private boolean isNetworkAvailable(View view) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}