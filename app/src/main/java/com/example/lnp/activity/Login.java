package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private TextView txtSignup, txtForgotPassword;
    private EditText editTextUserName, editTextPassword;
    private AppCompatButton btnLogin, btnGoggle;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FindViewBy Id
        txtSignup = findViewById(R.id.txtSignup);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoggle = findViewById(R.id.btnGoogle);

        sharedPreferences=getSharedPreferences("userInformation",MODE_PRIVATE);


        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToSignupActivity();
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToForgotPasswordActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextUserName.getText().toString().trim();
                String userPassword = editTextPassword.getText().toString().trim();
                if (isValidInput(userName, userPassword)) {
                    loginUser(userName, userPassword);
                }
            }


        });
    }

    /**
     * Initiates a login request with the provided phone number and password.
     *
     * @param userMobileNumber The phone number entered by the user.
     * @param userPassword     The password entered by the user.
     */

    private void loginUser(String userMobileNumber, String userPassword) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber", userMobileNumber);
            jsonObject.put("password", userPassword);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.LOGIN_API, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();
                sendToMainActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public void sendToSignupActivity() {
        Intent intent = new Intent(Login.this, SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void sendToForgotPasswordActivity() {
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void sendToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Checks if the provided username and password are valid.
     * True if both username and password are non-empty, otherwise false.
     */
    public boolean isValidInput(String userName, String password) {
        if (userName.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please Enter Email And Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName.isEmpty()) {
            editTextUserName.requestFocus();
            editTextUserName.setError("Enter UserName");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Enter Password");
            return false;
        }
        return true;

    }

}