package com.example.lnp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lnp.fragment.ChangePassword;
import com.example.lnp.fragment.DateOfBirthVerfication;
import com.example.lnp.fragment.MobileNumberVerification;

public class ForgotPasswordPagerAdapter extends FragmentPagerAdapter {
    public ForgotPasswordPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MobileNumberVerification();
            case 1:
                return new DateOfBirthVerfication();
            case 2:
                return new ChangePassword();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
