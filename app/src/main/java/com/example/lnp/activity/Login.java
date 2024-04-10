package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnp.R;

public class Login extends AppCompatActivity {
    private TextView txtSignup, txtForgotPassword;
    private EditText editTextUserName, editTextPassword;
    private AppCompatButton btnLogin, btnGoggle;

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
                if (isValidInput(
                        editTextUserName.getText().toString().trim(),
                        editTextPassword.getText().toString().trim()
                )) {
                    sendToMainActivity();
                }
            }
        });
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