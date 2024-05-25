package com.example.lnp.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.example.lnp.DataModel.UserInformationDataModel;
import com.example.lnp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    EditText editTextName,editTextMobileNumber,editTextAddress1,editTextAddress2,editTextAddress3,editTextEmail;
    AppCompatButton btnSubmit;
    private SharedPreferences sharedPreferences;

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

        sharedPreferences=getSharedPreferences("userInformation",MODE_PRIVATE);
        String mobileNumber=sharedPreferences.getString("mobileNumber",null);

        displayUserDetails(mobileNumber);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationDataModel userInformationDataModel =new UserInformationDataModel();
                String fullName=editTextName.getText().toString().trim();
                String mobileNumber=editTextMobileNumber.getText().toString().trim();
                String address=editTextAddress1.getText().toString().trim();
                String state=editTextAddress2.getText().toString().trim();
                String district=editTextAddress3.getText().toString().trim();
                String email=editTextEmail.getText().toString().trim();
                String[] name=fullName.split(" ");
                userInformationDataModel.setFirstName(name[0]);
                userInformationDataModel.setLastName(name[1]);
                userInformationDataModel.setMobileNumber(mobileNumber);
                userInformationDataModel.setEmail(email);
                userInformationDataModel.setAddress1(address);
                userInformationDataModel.setAddress1(address);
                userInformationDataModel.setState(state);
                userInformationDataModel.setDistrict(district);
                fetchData(mobileNumber, userInformationDataModel);
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
                            String[] address=userDetailsObj.getString("address").split("#");
                            editTextName.setText(userDetailsObj.getString("firstName")+" "+userDetailsObj.getString("lastName"));
                            editTextEmail.setText(userDetailsObj.getString("email"));
                            editTextMobileNumber.setText(response.getString("phoneNumber"));
                            editTextAddress1.setText(address[0]);
                            editTextAddress2.setText(address[1]);
                            editTextAddress3.setText(address[2]);
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

    public void fetchData(String mobileNumber, UserInformationDataModel userInformationDataModel){
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
                            userDetails.put("firstName", userInformationDataModel.getFirstName());
                            userDetails.put("lastName", userInformationDataModel.getLastName());
                            userDetails.put("email", userInformationDataModel.getEmail());
                            userDetails.put("address", userInformationDataModel.getAddress1()+"#"+userInformationDataModel.getState()+"#"+userInformationDataModel.getDistrict());


                            // Create the new body for PUT request
                            JSONObject updatedDetails = new JSONObject();
                            updatedDetails.put("userId", response.getInt("userId"));
                            updatedDetails.put("phoneNumber", userInformationDataModel.getMobileNumber());
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
                        Log.d("errorAPI","Error : "+error.toString());
                        Toast.makeText(EditProfile.this, "Failed to update record", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}