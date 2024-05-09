package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    TextView txtLogin;
    AppCompatButton btnSignup;
    EditText editTextMobileNumber, editTextPassword, editTextConfirmPassword;
    private static String url="http://localhost:8080/rest/auth/signup";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //FindViewById
        txtLogin = findViewById(R.id.txtLogin);
        btnSignup = findViewById(R.id.btnSignup);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLoginActivity();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInput(
                        editTextMobileNumber.getText().toString().trim(),
                        editTextPassword.getText().toString().trim(),
                        editTextConfirmPassword.getText().toString().trim()
                )) {
//                    addUser(url,
//                            editTextMobileNumber.getText().toString().trim(),
//                            editTextConfirmPassword.getText().toString().trim());
                    sendToUserInfoActivity();
                }
            }
        });
    }

    public void sendToLoginActivity() {
        Intent intent = new Intent(SignUp.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void sendToUserInfoActivity() {
        Intent intent = new Intent(SignUp.this, UserInformation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Checks if the provided mobileNumber and password are valid.
     * True if both username and password are non-empty, otherwise false.
     */

    public boolean isValidInput(String mobileNumber, String password, String confirmPassword) {
        if (mobileNumber.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mobileNumber.isEmpty()){
            editTextMobileNumber.requestFocus();
            editTextMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (mobileNumber.length()!=10) {
            editTextMobileNumber.requestFocus();
            editTextMobileNumber.setError("Mobile Number must be 10 digits");
            return false;
        }
        if(password.isEmpty()){
            editTextPassword.requestFocus();
            editTextPassword.setError("Enter Password");
            return false;
        }
        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Enter Confirm Password");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    public void addUser(String url,String userMobileNumber,String userPassword){
        RequestQueue requestQueue= Volley.newRequestQueue(SignUp.this);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone_number", userMobileNumber);
            jsonObject.put("password", userPassword);
        }catch (Exception e){

        }
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(SignUp.this, "Response : "+response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","error : "+error.toString());
            }
        });
        requestQueue.add(request);
    }
}