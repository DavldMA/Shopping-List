package com.example.shopping_list_application;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import static java.lang.Integer.parseInt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListsFragment extends Fragment{
    private AppDatabase db;

    private String baseURL = "https://shopping-list-api-five.vercel.app";

    private ListAdapter adapter;
    RecyclerView rv;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        db = AppDatabase.getInstance(view.getContext());
        rv = view.findViewById(R.id.rvLists);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        JSONObject data = new JSONObject();
        User loggedInUser = db.userDao().loadUser();
        if (loggedInUser != null && loggedInUser.isLogged() && isNetworkAvailable(view)) {
            String url = baseURL+"/list/all?username="+ loggedInUser.getUsername();
            APIRequests.GetData(url, view.getContext(), data, new APIRequests.ApiListener() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    Log.i("a","a"+response);
                    List<Lists> listdata = new ArrayList<>();
                    if(!db.listsDao().getAll().isEmpty()){
                        listdata=db.listsDao().getAll();
                    }
                    JSONArray jArray = response.getJSONArray("lists");

                    Log.i("a","a"+jArray);
                    if (jArray != null) {
                        for (int i=0;i<jArray.length();i++){
                            Gson gson = new GsonBuilder().create();
                            listdata.add(gson.fromJson(jArray.getJSONObject(i).toString(), Lists.class));
                        }
                    }
                    Log.d("POST Request", "Success: " + response.toString());
                    List<Lists> finalListdata = listdata;
                    CountDownLatch latch = new CountDownLatch(1); // Create a CountDownLatch with a count of 1
                    executorService.execute(() -> {
                        synchronized (this) {
                            db.runInTransaction(() -> {
                                for (Lists list : finalListdata) {
                                    int indiceUpdate = searchUserByName(db.listsDao().getAll(), list.getName());
                                    if (indiceUpdate == -1) {
                                        db.listsDao().insert(list);
                                    }
                                }
                            });
                        }
                        latch.countDown(); // Decrease the count of the latch, releasing all waiting threads when the count reaches zero
                    });
                    try {
                        latch.await(); // Current thread waits until the latch has counted down to zero
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rv.setAdapter(new ListAdapter(db.listsDao().getAll()));
                }
                @Override
                public void onError(String error) {
                    Log.e("POST Request", "Error: " + error);
                }
            });
        }
        else{
            rv.setAdapter(new ListAdapter(db.listsDao().getAll()));
        }
    }
    public ListsFragment() {}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvLists);
        registerForContextMenu(recyclerView);
        return view;

        //return inflater.inflate(R.layout.fragment_lists, container, false);

    }
//this needs to change the name of the variables
    private int searchUserByName(List<Lists> list, String username) {
        int indiceDelete = -1;
        for (Lists usr : list) {
            if (usr.getName().equals(username))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }
    private boolean isNetworkAvailable(View view) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
// Context MENU
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        rv = this.getView().findViewById(R.id.rvLists);
        adapter = (ListAdapter) rv.getAdapter();;
        List<Lists> lists = db.listsDao().getAll();
        Lists list = lists.get(item.getItemId());

        String title = item.getTitle().toString();
        if (title.equals(this.getContext().getApplicationContext().getResources().getString(R.string.share))) {
            if(isNetworkAvailable(this.getView())){
                JSONObject postData = new JSONObject();
                Gson gson = new GsonBuilder().create();
                try {
                    postData.put("username", db.userDao().loadUser().getUsername());
                    postData.put("list", gson.toJson(list));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                APIRequests.PostData(baseURL+"/list/share",this.getView().getContext(), postData, new APIRequests.ApiListener() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        Log.d("POST Request", "Success: " + response.toString());
                        switch(response.getString("CODE")){
                            case "001":
                                ClipboardManager clipboard = (ClipboardManager) ListsFragment.this.getContext().getSystemService(ListsFragment.this.getView().getContext().CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.url), response.getString("url"));
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ListsFragment.this.getContext(), R.string.copied_to_clipboard, Toast.LENGTH_LONG).show();
                                break;
                            case "002":
                                //error creating URL
                                break;
                        }
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("POST Request", "Error: " + error);
                    }
                });
            }
            return true;
        }
        else if (title.equals(this.getContext().getApplicationContext().getResources().getString(R.string.delete))) {
            if(isNetworkAvailable(this.getView())){
                JSONObject postData = new JSONObject();
                Gson gson = new GsonBuilder().create();
                try {
                    postData.put("username", db.userDao().loadUser().getUsername());
                    postData.put("list", gson.toJson(list));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                APIRequests.DeleteData(baseURL+"/list/removelist/"+db.userDao().loadUser().getUsername()+"/"+list.getName(),this.getView().getContext(), postData, new APIRequests.ApiListener() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        Log.d("POST Request", "Success: " + response.toString());
                        switch(response.getString("CODE")){
                            case "001":
                                db.listsDao().delete(list);
                                adapter.ListSetter(db.listsDao().getAll());
                                break;
                        }
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("POST Request", "Error: " + error);
                    }
                });
            }
            else{
                db.listsDao().delete(list);
                adapter.ListSetter(db.listsDao().getAll());
            }
            return true;
        }
        return true;
    }
}