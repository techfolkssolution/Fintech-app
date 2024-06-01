package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.UserInformationDataModel;
import com.example.lnp.Interface.UserRecordsCallback;
import com.example.lnp.R;
import com.example.lnp.adapter.AllUserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerificationRequest extends AppCompatActivity {
    RecyclerView recyclerVerificationRequest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_request);

        //FindViewById
        recyclerVerificationRequest = findViewById(R.id.recyclerVerificationRequest);

        getPendingUser(new UserRecordsCallback() {
            @Override
            public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                AllUserAdapter userAdapter = new AllUserAdapter(VerificationRequest.this, userInformationDataModelArrayList);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerVerificationRequest.setLayoutManager(manager);
                recyclerVerificationRequest.setAdapter(userAdapter);
            }

            @Override
            public void pageCounter(int pageNumber) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void getPendingUser(final UserRecordsCallback callback) {
        ArrayList<UserInformationDataModel> userInformationDataModelArrayList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API.API_PENDING_VERIFICATION_REQUEST, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try{
                        JSONObject record=response.getJSONObject(i);
                        UserInformationDataModel userInfo=new UserInformationDataModel();
                        userInfo.setEmail(record.getString("email"));
                        userInfo.setFirstName(record.getString("firstName"));
                        userInfo.setLastName(record.getString("lastName"));
                        userInfo.setGender(record.getString("gender"));
                        userInfo.setDateOfBirth(record.getString("dob"));
                        userInfo.setDesignation(record.getString("designation"));
                        String[] fullAddress = record.getString("address").split("#");
                        userInfo.setAddress1(fullAddress[0]);
                        userInfo.setState(fullAddress[1]);
                        userInfo.setDistrict(fullAddress[2]);
                        userInformationDataModelArrayList.add(userInfo);

                    }catch (JSONException jsonException){

                    }

                }
                callback.onSuccess(userInformationDataModelArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }
}