package com.example.lnp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnp.R;
import com.example.lnp.activity.Login;


public class ChangePassword extends Fragment {
    AppCompatButton btnSubmit;


    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
        return view;
    }


    private void onSubmitButtonClick() {
        // Your logic for changing the password
        navigateToLoginActivity();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish(); // Optionally, finish the current activity
    }
}