package com.example.shopping_list_application;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class APIRequests {

    private static final String API_URL = "https://pokemon-type-api.vercel.app/pokemon/fire";
    private static final String TAG = "PokemonApiRequest";

    public interface PokemonApiListener {
        void onSuccess(JSONObject response);
        void onError(String error);
    }

    public static void fetchPokemonData(Context context, final PokemonApiListener listener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response (update UI, parse JSON, etc.)
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors (e.g., network error, server error)
                        listener.onError(error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}