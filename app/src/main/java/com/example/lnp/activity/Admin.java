package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.AdminAccessInformation;
import com.example.lnp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Admin extends AppCompatActivity {
    private ArrayList<AdminAccessInformation> adminAccessInformation;
    private CardView cardViewAllUserInformation, cardViewVerificationRequest, cardViewCustomerSupport;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        cardViewAllUserInformation = findViewById(R.id.cardViewAllUserInformation);
        cardViewVerificationRequest = findViewById(R.id.cardViewVerificationRequest);
        cardViewCustomerSupport = findViewById(R.id.cardViewCustomerSupport);

        cardViewAllUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, AllUserInformation.class);
                startActivity(i);
                finish();
            }
        });

        cardViewVerificationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, VerificationRequest.class);
                startActivity(i);
                finish();
            }
        });


        getData(new Callback() {
            @Override
            public void onSuccess(ArrayList<AdminAccessInformation> adminAccessInformations) {
                adminAccessInformation = adminAccessInformations;
            }
        });


        cardViewCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(adminAccessInformation);
            }
        });


    }

    public void displayDialog(ArrayList<AdminAccessInformation> adminAccessInformationsArrayList) {
        Dialog dialog = new Dialog(Admin.this);
        dialog.setContentView(R.layout.customer_support_dialog);

        AutoCompleteTextView spinnerCustomerServiceType = dialog.findViewById(R.id.customerServiceType);
        EditText editText = dialog.findViewById(R.id.editText);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSubmit);

        ArrayList<String> customerService = new ArrayList<>();
        for (AdminAccessInformation admin : adminAccessInformationsArrayList) {
            customerService.add(admin.getAdminAccessInformationKey());
        }
        ArrayAdapter<String> customerServiceTypeAdapter = new ArrayAdapter<>(Admin.this, R.layout.drop_down_item, customerService);
        spinnerCustomerServiceType.setAdapter(customerServiceTypeAdapter);


        spinnerCustomerServiceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(adminAccessInformationsArrayList.get(i).getAdminAccessInformationValue());
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = spinnerCustomerServiceType.getText().toString();
                String value=editText.getText().toString().trim();
                updateData(key,value);
            }
        });


        dialog.show();
    }

    public void getData(Callback callback) {
        ArrayList<AdminAccessInformation> adminAccessInformations = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API.API_CONTACT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    AdminAccessInformation adminAccessInformation = new AdminAccessInformation();
                    try {
                        JSONObject data = response.getJSONObject(i);
                        adminAccessInformation.setId(data.getInt("id"));
                        adminAccessInformation.setAdminAccessInformationKey(data.getString("adminAccessInformationKey"));
                        adminAccessInformation.setAdminAccessInformationValue(data.getString("adminAccessInformationValue"));
                        adminAccessInformations.add(adminAccessInformation);
                    } catch (JSONException e) {

                    }
                }
                callback.onSuccess(adminAccessInformations);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(jsonArrayRequest);

    }


    public void updateData(String key, String value) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String apiUrl = API.API_CONTACT_UPDATE + key + "&value=" + value;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Admin.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

    }

    public interface Callback {
        public void onSuccess(ArrayList<AdminAccessInformation> adminAccessInformations);
    }

}
