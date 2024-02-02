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


}


/*
 public static void fetchData(Context context, final ApiListener listener) {
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
 */







/*


    public static void postData(Context context, JSONObject postData, final ApiListener listener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, API_URL, postData,
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







    JSONObject postData = new JSONObject();
try {
        postData.put("key1", "value1");
        postData.put("key2", "value2");
        } catch (JSONException e) {
        e.printStackTrace();
        }

        APIRequests.postData(context, postData, new APIRequests.ApiListener() {
@Override
public void onSuccess(JSONObject response) {
        // Handle the success response
        Log.d("POST Request", "Success: " + response.toString());
        }

@Override
public void onError(String error) {
        // Handle the error response
        Log.e("POST Request", "Error: " + error);
        }
        });
 */