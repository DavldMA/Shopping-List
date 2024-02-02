package com.example.shopping_list_application;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.buttonRegister);
        Button goBackButton = findViewById(R.id.btnGoBack);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                Log.i("teste", ""+email);

                if(isValidEmail(email) && email != null && email != ""){
                    if(password.equals(confirmPassword)){
                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("username", username);
                            postData.put("email", email);
                            postData.put("password", password);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        APIRequests.PostData("https://shopping-list-api-beta.vercel.app/user/register",RegisterActivity.this, postData, new APIRequests.ApiListener() {
                            @Override
                            public void onSuccess(JSONObject response) throws JSONException {;
                                if(response.getString("CODE") != null){
                                    switch(response.getString("CODE")){
                                        case "001":
                                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        break;
                                        case "002":
                                            Toast.makeText(RegisterActivity.this, "Username Already Exists", Toast.LENGTH_LONG).show();
                                        break;
                                        case "003":
                                            Toast.makeText(RegisterActivity.this, "Email Already Registered", Toast.LENGTH_LONG).show();
                                        break;
                                        case "004":
                                            Toast.makeText(RegisterActivity.this, "The password Inserted is wrong", Toast.LENGTH_LONG).show();
                                            break;
                                        case "005":
                                            Toast.makeText(RegisterActivity.this, "The Email is wrong or does't exist", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(RegisterActivity.this, "Unknown ERROR", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                                Log.d("POST Request", "Success: " + response.toString());
                            }
                            @Override
                            public void onError(String error) {
                                // Handle the error response
                                Log.e("POST Request", "Error: " + error);
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
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
}