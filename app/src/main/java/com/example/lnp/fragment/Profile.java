package com.example.lnp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.R;
import com.example.lnp.activity.EditProfile;

import org.json.JSONException;
import org.json.JSONObject;


public class Profile extends Fragment {

    AppCompatButton btnWallet, btnEditProfile;
    FragmentManager fragmentManager;
    TextView textViewUserId, textViewName, textViewDesignation, textViewEmail, textViewMobileNumber, textViewAddress1, textViewAddress2, textViewAddress3;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnWallet = view.findViewById(R.id.btnWallet);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        textViewUserId = view.findViewById(R.id.textViewUserId);
        textViewName = view.findViewById(R.id.textViewName);
        textViewDesignation = view.findViewById(R.id.textViewDesignation);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewMobileNumber = view.findViewById(R.id.textViewMobileNumber);
        textViewAddress1 = view.findViewById(R.id.textViewAddress1);
        textViewAddress2 = view.findViewById(R.id.textViewAddress2);
        textViewAddress3 = view.findViewById(R.id.textViewAddress3);


        // Get the FragmentManager
        fragmentManager = getParentFragmentManager();


        //This Method Will Display User Details In Textview.
        displayUserDetails("8780260413");

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wallet wallet = new Wallet();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, wallet)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);
            }
        });


        return view;
    }


    public void displayUserDetails(String mobileNumber) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API.READ_USER_DETAILS_API + mobileNumber,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject userDetailsObj = response.getJSONObject("userDetails");
                            textViewUserId.setText(String.valueOf(response.getInt("userId")));
                            textViewName.setText(userDetailsObj.getString("firstName")+" "+userDetailsObj.getString("lastName"));
                            textViewDesignation.setText(userDetailsObj.getString("designation"));
                            textViewEmail.setText(userDetailsObj.getString("email"));
                            textViewMobileNumber.setText(response.getString("phoneNumber"));
                            textViewAddress1.setText(userDetailsObj.getString("address"));

                        }catch (JSONException jsonException){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }

    public void readDataFromAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API.READ_USER_DETAILS_API + "8780260413",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userDetailsObj = response.getJSONObject("userDetails");
                            Log.d("API Checking", "User Id : " + response.getInt("userId") +
                                    "User Name : " + userDetailsObj.getString("firstName"));

                        } catch (JSONException exception) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API Checking", "error : " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}