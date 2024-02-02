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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("aas", "as "+ item.getItemId());
                switch(item.getItemId()){
                    //Lists
                    case 2131230979:
                        Bundle bundle = new Bundle();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, ListsFragment.class, bundle)
                                .commit();
                        break;
                    //create
                    case 2131230861:
                        Bundle nbundle = new Bundle();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, CreateListsFragment.class, nbundle)
                                .commit();
                        break;
                    //settings
                    case 1:
                       /* Bundle bundle = new Bundle();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, Lista.class, bundle)
                                .commit();*/
                        break;
                    //Login
                    case 2131230980:
                        loggedInUser.setLogged(false);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
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