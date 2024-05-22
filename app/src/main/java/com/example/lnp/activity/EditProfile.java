package com.example.lnp.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.UserInformation;
import com.example.lnp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    EditText editTextName,editTextMobileNumber,editTextAddress1,editTextAddress2,editTextAddress3,editTextEmail;
    AppCompatButton btnSubmit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //FindViewBy Id
        editTextName=findViewById(R.id.editTextName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextMobileNumber=findViewById(R.id.editTextMobileNumber);
        editTextAddress1=findViewById(R.id.editTextAddress01);
        editTextAddress2=findViewById(R.id.editTextAddress02);
        editTextAddress3=findViewById(R.id.editTextAddress03);
        btnSubmit =findViewById(R.id.btnSubmit);

        displayUserDetails("8780260413");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation userInformation=new UserInformation();
                String fullName=editTextName.getText().toString().trim();
                String mobileNumber=editTextMobileNumber.getText().toString().trim();
                String address=editTextAddress1.getText().toString().trim();
                String email=editTextEmail.getText().toString().trim();
                String[] name=fullName.split(" ");
                userInformation.setFirstName(name[0]);
                userInformation.setLastName(name[1]);
                userInformation.setMobileNumber(mobileNumber);
                userInformation.setEmail(email);
                userInformation.setAddress1(address);
                fetchData("8780260413",userInformation);
            }
        });

    }
    public void displayUserDetails(String mobileNumber){
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API.READ_USER_DETAILS_API + mobileNumber,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userDetailsObj = response.getJSONObject("userDetails");
                            editTextName.setText(userDetailsObj.getString("firstName")+" "+userDetailsObj.getString("lastName"));
                            editTextEmail.setText(userDetailsObj.getString("email"));
                            editTextMobileNumber.setText(response.getString("phoneNumber"));
                            editTextAddress1.setText(userDetailsObj.getString("address"));
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

    public void fetchData(String mobileNumber,UserInformation userInformation){
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API.READ_USER_DETAILS_API + mobileNumber,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userDetails = response.getJSONObject("userDetails");

                            // Modify the userDetails
                            userDetails.put("firstName", userInformation.getFirstName());
                            userDetails.put("lastName", userInformation.getLastName());
                            userDetails.put("email", userInformation.getEmail());
                            userDetails.put("address", userInformation.getAddress1());


                            // Create the new body for PUT request
                            JSONObject updatedDetails = new JSONObject();
                            updatedDetails.put("userId", response.getInt("userId"));
                            updatedDetails.put("phoneNumber", userInformation.getMobileNumber());
                            updatedDetails.put("password", response.getString("password"));
                            updatedDetails.put("userRole", response.optString("userRole", null));
                            updatedDetails.put("userDetails", userDetails);

                            // Update the record
                            updateRecord(updatedDetails);

                        }catch (JSONException exception){

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

    private void updateRecord(JSONObject updatedDetails) {
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                API.UPDATE_USER_INFORMATION_API,
                updatedDetails,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Record updated successfully");
                        Toast.makeText(EditProfile.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(EditProfile.this, "Failed to update record", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}