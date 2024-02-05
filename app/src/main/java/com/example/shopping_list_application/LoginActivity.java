package com.example.shopping_list_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FloatingActionButton goBackButton = findViewById(R.id.btnGoBack);
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button button = findViewById(R.id.buttonA);// botão de registrar

        db = AppDatabase.getInstance(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(isValidEmail(email) && email != null && email != ""){
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("email", email);
                        postData.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    APIRequests.PostData("https://shopping-list-api-beta.vercel.app/user/login",LoginActivity.this, postData, new APIRequests.ApiListener() {
                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {
                            Log.d("POST Request", "Success: " + response.toString());
                            if(response.getString("CODE") != null){
                                switch(response.getString("CODE")){
                                    case "001":
                                        Toast.makeText(LoginActivity.this, getString(R.string.erro1), Toast.LENGTH_LONG).show();
                                        User user = new User(response.getString("username"),email);
                                        Log.d("d", ""+user.getUsername());
                                        executorService.execute(() -> {
                                            List<User> users = db.userDao().getAll();
                                            int indiceUpdate = searchUserByName(users, user.getUsername());
                                            if (indiceUpdate != -1) {
                                                user.setId(users.get(indiceUpdate).getId());
                                                db.userDao().update(user);
                                            } else {
                                                db.userDao().insert(user);
                                            }
                                        });
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        break;
                                   /* case "002":
                                        Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                                        break;
                                    case "003":
                                        Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                                        break;*/
                                    case "004":
                                        Toast.makeText(LoginActivity.this, getString(R.string.erro2), Toast.LENGTH_LONG).show();
                                        break;
                                    case "005":
                                        Toast.makeText(LoginActivity.this, getString(R.string.erro3), Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, getString(R.string.erro4), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        }
                        @Override
                        public void onError(String error) {

                            Log.e("POST Request", "Error: " + error);
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    private int searchUserByName(List<User> list, String username) {
        int indiceDelete = -1;
        for (User usr : list) {
            if (usr.getUsername().equals(username))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }

}