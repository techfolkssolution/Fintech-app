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
import android.widget.EditText;

import com.example.lnp.Interface.OnMobileNumberSubmitListener;
import com.example.lnp.R;


public class MobileNumberVerification extends Fragment {
    private AppCompatButton btnSubmit;
    private OnMobileNumberSubmitListener mListener;
    private EditText editTextMobileNumber;


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
        //FindViewBy Id

        btnSubmit = view.findViewById(R.id.btnSubmit);
        editTextMobileNumber=view.findViewById(R.id.editTextMobileNumber);


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
        if (isValidMobileNumber(editTextMobileNumber.getText().toString().trim())) {
            // Call interface method to notify activity
            mListener.onMobileNumberSubmit();
        } else {
            // Show error message
        }
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        // Your validation logic here
        if(mobileNumber.isEmpty()){
            editTextMobileNumber.requestFocus();
            editTextMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (mobileNumber.length()!=10) {
            editTextMobileNumber.requestFocus();
            editTextMobileNumber.setError("Mobile Number must be 10 digits");
            return false;
        }
        return true; // Return true for testing purposes
    }
}