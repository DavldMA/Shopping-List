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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListsFragment extends Fragment {
    private AppDatabase db;
    RecyclerView rv;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        db = AppDatabase.getInstance(view.getContext());

        rv = view.findViewById(R.id.rvLists);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        JSONObject data = new JSONObject();
        try {
            data.put("username", "john_doe");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User loggedInUser = db.userDao().loadUser();

        if (loggedInUser != null && loggedInUser.isLogged() && isNetworkAvailable()) {
            String url = "https://shopping-list-api-beta.vercel.app/list/all?username=john_doe"; /*+ loggedInUser.getUsername();*/
            APIRequests.GetData(url, view.getContext(), data, new APIRequests.ApiListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        List<Lists> listdata = new ArrayList<>();
                        if(db.listsDao().getAll().size() != 0)
                            listdata = db.listsDao().getAll();

                        JSONArray jArray = response.optJSONArray("lists");
                        if (jArray != null) {
                            for (int i=0;i<jArray.length();i++){
                                Gson gson = new GsonBuilder().create();
                                listdata.add(gson.fromJson(jArray.getJSONObject(i).toString(), Lists.class));
                            }
                            int counter = 0;
                            List<Lists> finalListdata = listdata;
                            for(Lists list : finalListdata){
                                counter++;
                                list.setId(counter);
                                Log.d("TESTE", ""+list.getId());
                                executorService.execute(() -> {
                                    synchronized (this) {
                                        int indiceUpdate = searchUserByName(finalListdata, list.getName());
                                        if (indiceUpdate != -1) {
                                            list.setId(finalListdata.get(indiceUpdate).getId());
                                            db.listsDao().update(list);
                                        } else {
                                            Log.d("POST Request", "Success: " + response.toString());
                                            db.listsDao().insert(list);
                                        }
                                    }
                                });
                            }
                            Log.d("POST Request", "Success: " + response.toString());
                            rv.setAdapter(new ListAdapter(finalListdata));
                        }
                    } catch (JSONException e) {
                        Log.e("POST Request", "Error parsing JSON: " + e.getMessage());
                    }
                }
                @Override
                public void onError(String error) {
                    Log.e("POST Request", "Error: " + error);
                }
            });
        }
        else{
            Log.i("a","as");
            rv.setAdapter(new ListAdapter(db.listsDao().getAll()));
        }

        Log.d("POST Request", "Error: " + db.listsDao().getAll().size());
        //rv.setAdapter(new ListAdapter(listdata));
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

    private int searchUserByName(List<Lists> list, String username) {
        int indiceDelete = -1;
        for (Lists usr : list) {
            if (usr.getName().equals(username))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }
    private boolean isNetworkAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}