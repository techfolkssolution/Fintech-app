package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserInformation extends AppCompatActivity {
    private TextView txtDateOfBirth;
    private EditText editTextFirstName,editTextLastName,editTextEmail,editTextAddress,editTextState,editTextDistrict;

    private RadioButton radioButtonMale,radioButtonFemale;
    private AppCompatButton btnSubmit;
    private SharedPreferences sharedPreferences;
    String gender;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        //FindViewById
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        editTextFirstName=findViewById(R.id.editTextFirstName);
        editTextLastName=findViewById(R.id.editTextLastName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextAddress=findViewById(R.id.editTextAddress);
        editTextDistrict=findViewById(R.id.editTextDistrict);
        editTextState=findViewById(R.id.editTextState);
        radioButtonMale=findViewById(R.id.radioButtonMale);
        radioButtonFemale=findViewById(R.id.radioButtonFemale);
        btnSubmit=findViewById(R.id.btnSubmit);


        sharedPreferences=getSharedPreferences("userInformation",MODE_PRIVATE);

        // Set the current date in the TextView
        setCurrentDateInTextView();


        txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButtonMale.isChecked()){
                    gender="MALE";
                } else if (radioButtonFemale.isChecked()) {
                    gender="FEMALE";
                }
                UserInformationDataModel userInformationDataModel =new UserInformationDataModel();
                String firstName=editTextFirstName.getText().toString().trim();
                String lastName=editTextLastName.getText().toString().trim();
                String email=editTextEmail.getText().toString().trim();
                String dateOfBirth=txtDateOfBirth.getText().toString();
                String address=editTextAddress.getText().toString().trim();
                String district=editTextDistrict.getText().toString().trim();
                String state=editTextState.getText().toString().trim();
                userInformationDataModel.setFirstName(firstName);
                userInformationDataModel.setLastName(lastName);
                userInformationDataModel.setEmail(email);
                userInformationDataModel.setDateOfBirth(dateOfBirth);
                userInformationDataModel.setGender(gender);
                userInformationDataModel.setAddress1(address);
                userInformationDataModel.setState(state);
                userInformationDataModel.setDistrict(district);
                int userId=sharedPreferences.getInt("userId",0);
                Log.d("userId","User Id  "+userId);
                saveUserInformation(userId, userInformationDataModel);
            }
        });

    }

    public void setCurrentDateInTextView() {
        // Get the current date
        Date currentDate = new Date();

        // Create a SimpleDateFormat instance with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the current date
        String formattedDate = dateFormat.format(currentDate);

        // Set the formatted date to the TextView
        txtDateOfBirth.setText(formattedDate);
    }

    private void showDatePickerDialog() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set selected date to TextView
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        txtDateOfBirth.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Show DatePickerDialog
        datePickerDialog.show();
    }

    public void saveUserInformation(int userId, UserInformationDataModel userInformationDataModel){
        String address=userInformationDataModel.getAddress1()+"#"+userInformationDataModel.getState()+"#"+userInformationDataModel.getDistrict();
        RequestQueue queue= Volley.newRequestQueue(UserInformation.this);
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("userId",userId);
            jsonObject.put("email", userInformationDataModel.getEmail());
            jsonObject.put("firstName", userInformationDataModel.getFirstName());
            jsonObject.put("lastName", userInformationDataModel.getLastName());
            jsonObject.put("gender", userInformationDataModel.getGender());
            jsonObject.put("address", address);
            jsonObject.put("dob", userInformationDataModel.getDateOfBirth());
            jsonObject.put("designation","User");
            Log.d("saveUserInformation", "Request JSON: " + jsonObject.toString());
        }catch (JSONException jsonException){
            Log.d("exception","error : "+jsonException.toString());
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, API.SAVE_USER_INFORMATION_API, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sharedPreferences.edit().putBoolean("userInfo",true).apply();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                Toast.makeText(UserInformation.this, "User Information Data Saved", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","error : "+error.toString());
                Toast.makeText(UserInformation.this, "Error :"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }


}