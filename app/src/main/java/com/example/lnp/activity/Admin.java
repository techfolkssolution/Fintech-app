package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import com.example.lnp.R;


public class Admin extends AppCompatActivity {
    private  CardView cardViewAllUserInformation,cardViewVerificationRequest,cardViewCustomerSupport;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        cardViewAllUserInformation=findViewById(R.id.cardViewAllUserInformation);
        cardViewVerificationRequest=findViewById(R.id.cardViewVerificationRequest);
        cardViewCustomerSupport=findViewById(R.id.cardViewCustomerSupport);

        cardViewAllUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Admin.this,AllUserInformation.class);
                startActivity(i);
                finish();
            }
        });

        cardViewVerificationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Admin.this,VerificationRequest.class);
                startActivity(i);
                finish();
            }
        });

        cardViewCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });


    }

    public void displayDialog(){
        Dialog dialog=new Dialog(Admin.this);
        dialog.setContentView(R.layout.customer_support_dialog);


        dialog.show();
    }

}