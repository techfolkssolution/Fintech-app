package com.example.lnp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lnp.R;
import com.example.lnp.activity.Login;


public class ChangePassword extends Fragment {
    AppCompatButton btnSubmit;
    EditText editTextPassword,editTextConfirmPassword;


    public ChangePassword() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        //FindViewById
        btnSubmit = view.findViewById(R.id.btnSubmit);
        editTextPassword=view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword=view.findViewById(R.id.editTextConfirmPassword);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid(
                        editTextPassword.getText().toString().trim(),
                        editTextConfirmPassword.getText().toString().trim()
                )){
                    navigateToLoginActivity();
                }
            }
        });
        return view;
    }




    private void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish(); // Optionally, finish the current activity
    }

    public boolean isValid(String password,String confirmPassword){
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
}