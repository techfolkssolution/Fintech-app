package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.lnp.Interface.OnDateOfBirthSubmitListener;
import com.example.lnp.Interface.OnMobileNumberSubmitListener;
import com.example.lnp.R;
import com.example.lnp.adapter.ForgotPasswordPagerAdapter;

public class ForgotPassword extends AppCompatActivity implements OnMobileNumberSubmitListener, OnDateOfBirthSubmitListener {
    private  ViewPager viewPager;
    private ForgotPasswordPagerAdapter forgotPasswordPagerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        viewPager=findViewById(R.id.viewPager);
        forgotPasswordPagerAdapter=new ForgotPasswordPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(forgotPasswordPagerAdapter);
    }


    // Method to change the current fragment in the ViewPager
    public void changeFragment(int position) {
        viewPager.setCurrentItem(position);
    }
    // Method called when submit button is clicked in Mobile Number fragment
    @Override
    public void onMobileNumberSubmit() {
        // Change to Date of Birth fragment
        changeFragment(1);
    }

    @Override
    public void onDateOfBirthSubmit() {
        changeFragment(2);
    }
}