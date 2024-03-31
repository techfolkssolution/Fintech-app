package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lnp.R;

public class Login extends AppCompatActivity {
    TextView txtSignup,txtForgotPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtSignup=findViewById(R.id.txtSignup);
        txtForgotPassword=findViewById(R.id.txtForgotPassword);

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
    }
    public void sendToSignupActivity(){
        Intent intent=new Intent(Login.this,SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void sendToForgotPasswordActivity(){
        Intent intent=new Intent(Login.this,ForgotPassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}