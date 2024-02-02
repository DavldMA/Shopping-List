package com.example.shopping_list_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListsFragment extends Fragment {
    private AppDatabase db;
    RecyclerView rv;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        rv = view.findViewById(R.id.rvLists);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        JSONObject data = new JSONObject();
        try {
            data.put("username", "john_doe");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("a","asdf Movie");
        APIRequests.GetData("https://shopping-list-api-beta.vercel.app/list/all?username=john_doe", view.getContext(), data, new APIRequests.ApiListener() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                Log.i("a","a"+response);

                ArrayList<Lists> listdata = new ArrayList<>();

                JSONArray jArray = response.getJSONArray("lists");


                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        Gson gson = new GsonBuilder().create();
                        listdata.add(gson.fromJson(jArray.getJSONObject(i).toString(), Lists.class));
                    }
                }

                Log.d("aasdasdasd", ""+ listdata.get(1).getName());

                rv.setAdapter(new ListAdapter(listdata));
                Log.d("POST Request", "Success: " + response.toString());
            }

            @Override
            public void onError(String error) {

                Log.e("POST Request", "Error: " + error);
            }
        });
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