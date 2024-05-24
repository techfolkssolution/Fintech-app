package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.lnp.R;

public class SplashScreen extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences=getSharedPreferences("userInformation",MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("isLoggedIn",false))  {
                    sendToMainActivity();
                } else if (!sharedPreferences.getBoolean("userInfo",true)) {
                    sendToUserInformationActivity();
                } else{
                    sendToLoginActivity();
                }
            }
        },2500);

    }
    public void sendToLoginActivity(){
        Intent intent=new Intent(SplashScreen.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void sendToMainActivity(){
        Intent intent=new Intent(SplashScreen.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void sendToUserInformationActivity(){
        Intent intent=new Intent(SplashScreen.this,UserInformation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}