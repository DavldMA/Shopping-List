package com.example.shopping_list_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
    }
}