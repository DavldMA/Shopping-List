package com.example.shopping_list_application;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        if (loggedInUser != null && loggedInUser.isLogged() && isNetworkAvailable(view)) {
            String url = "https://shopping-list-api-beta.vercel.app/list/all?username=john_doe"; /*+ loggedInUser.getUsername();*/
            APIRequests.GetData(url, view.getContext(), data, new APIRequests.ApiListener() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    Log.i("a","a"+response);

                    List<Lists> listdata = new ArrayList<>();

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

                    List<Lists> finalListdata = listdata;
                    executorService.execute(() -> {
                        synchronized (this) {
                            db.runInTransaction(() -> {
                                for (Lists list : finalListdata) {
                                    Log.d("TESTE", "" + list.getId());
                                    int indiceUpdate = searchUserByName(db.listsDao().getAll(), list.getName());
                                    if (indiceUpdate == -1) {
                                        Log.d("POST Request", "Success: " + response.toString());
                                        db.listsDao().insert(list);
                                    }
                                }
                            });
                        }
                    });

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
            Log.i("ALTOS TESTES MEU DEUS FUNCIONA", "" + usr.getName());
            if (usr.getName().equals(username))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }


    private boolean isNetworkAvailable(View view) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}