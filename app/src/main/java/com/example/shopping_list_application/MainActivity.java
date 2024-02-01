package com.example.shopping_list_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        APIRequests.fetchData(this, new APIRequests.ApiListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i("string", "" + response);
            }

            @Override
            public void onError(String error) {
                Log.i("string", "" + error);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("aas", "as "+ item.getItemId());
                switch(item.getItemId()){
                    case 2131230967:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return false;
                }
                return true;
            }
        });
    }
}



/*
        }
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();

@Override
public boolean
        onNavigationItemSelected(@NonNull MenuItem item)
        {

        switch (item.getItemId()) {
        case R.id.person:
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.flFragment, firstFragment)
        .commit();
        return true;

        case R.id.home:
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.flFragment, secondFragment)
        .commit();
        return true;

        case R.id.settings:
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.flFragment, thirdFragment)
        .commit();
        return true;
        }
        return false;
        }
*/
