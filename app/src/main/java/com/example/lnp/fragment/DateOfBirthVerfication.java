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

import com.example.lnp.Interface.OnDateOfBirthSubmitListener;
import com.example.lnp.Interface.OnMobileNumberSubmitListener;
import com.example.lnp.R;


public class DateOfBirthVerfication extends Fragment {
    private AppCompatButton btnVerify;
    private OnDateOfBirthSubmitListener listener;

    public DateOfBirthVerfication() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnDateOfBirthSubmitListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMobileNumberSubmitListener");
        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_of_birth_verfication, container, false);
        btnVerify = view.findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
        return view;
    }

    private void onSubmitButtonClick() {
        listener.onDateOfBirthSubmit();
    }
}