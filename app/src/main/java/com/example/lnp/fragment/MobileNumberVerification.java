package com.example.lnp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnp.Interface.OnMobileNumberSubmitListener;
import com.example.lnp.R;


public class MobileNumberVerification extends Fragment {
    private AppCompatButton btnSubmit;
    private OnMobileNumberSubmitListener mListener;


    public MobileNumberVerification() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnMobileNumberSubmitListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMobileNumberSubmitListener");
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_number_verification, container, false);
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
        // Validate mobile number
        if (isValidMobileNumber()) {
            // Call interface method to notify activity
            mListener.onMobileNumberSubmit();
        } else {
            // Show error message
        }
    }

    private boolean isValidMobileNumber() {
        // Your validation logic here
        return true; // Return true for testing purposes
    }
}