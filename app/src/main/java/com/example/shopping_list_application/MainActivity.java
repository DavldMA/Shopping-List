package com.example.shopping_list_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_list_application.placeholder.ListDetailsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity{

    private AppDatabase db;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = AppDatabase.getInstance(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        TextView tv = findViewById(R.id.textView);

        User loggedInUser = db.userDao().loadUser();

        if (loggedInUser != null && loggedInUser.isLogged()) {
            tv.setText(loggedInUser.getUsername());
        } else {
            //login
            Intent intent = new Intent(MainActivity.this, ListDetailsActivity.class);
            startActivity(intent);
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("aas", "as "+ item.getItemId());
                //Lists
                if(item.getItemId() == R.id.lists){
                    Bundle bundle = new Bundle();
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainerView, ListsFragment.class, bundle)
                            .commit();
                    return true;
                }
                //Create List
                else if(item.getItemId() == R.id.create){
                    Bundle nbundle = new Bundle();
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainerView, CreateListsFragment.class, nbundle)
                            .commit();
                    return true;
                }
                //Login
                else if (item.getItemId() == R.id.loginMenuBtn) {
                    loggedInUser.setLogged(false);
                    db.userDao().delete(db.userDao().loadUser());
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                else{
                    return true;
                }
            }
        });
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