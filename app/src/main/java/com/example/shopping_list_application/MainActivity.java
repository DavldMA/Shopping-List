package com.example.shopping_list_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

                int id = item.getItemId();
                Log.i("teste", ""+item);
                switch(id){
                    case 2131231075:
                        //Ver Listas
                        break;
                    case 2131230934:
                        //Criar
                        break;
                    case 2131231116:
                        //Settings
                        break;
                    case 2131230965:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent, null);
                        break;
                    default:
                        Log.i("error", ""+id);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.person);
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
