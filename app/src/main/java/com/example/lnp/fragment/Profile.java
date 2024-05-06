package com.example.lnp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnp.R;
import com.example.lnp.activity.EditProfile;


public class Profile extends Fragment {

    AppCompatButton btnWallet,btnEditProfile;
    FragmentManager fragmentManager;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnWallet=view.findViewById(R.id.btnWallet);
        btnEditProfile=view.findViewById(R.id.btnEditProfile);

        // Get the FragmentManager
        fragmentManager = getParentFragmentManager();

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wallet wallet=new Wallet();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, wallet)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), EditProfile.class);
                startActivity(i);
            }
        });

        return view;
    }
}