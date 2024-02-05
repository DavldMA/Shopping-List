package com.example.shopping_list_application;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class APIRequests {

    private static final String API_URL = "https://shopping-list-api-beta.vercel.app/user/login";

    public interface ApiListener {
        void onSuccess(JSONObject response) throws JSONException;
        void onError(String error);
    }

    public static void GetData(String URL,Context context, JSONObject postData, final ApiListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listener.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public static void PostData(String URL, Context context, JSONObject postData, final ApiListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listener.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public static void DeleteData(String URL,Context context, JSONObject postData, final ApiListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listener.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

}