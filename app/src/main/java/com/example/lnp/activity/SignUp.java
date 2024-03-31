package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lnp.R;

public class SignUp extends AppCompatActivity {
    TextView txtLogin;
    AppCompatButton btnSignup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtLogin=findViewById(R.id.txtLogin);
        btnSignup=findViewById(R.id.btnSignup);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLoginActivity();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToUserInfoActivity();
            }
        });
    }
    public void sendToLoginActivity(){
        Intent intent=new Intent(SignUp.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void sendToUserInfoActivity(){
        Intent intent=new Intent(SignUp.this,UserInformation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}