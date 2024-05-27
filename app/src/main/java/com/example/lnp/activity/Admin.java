package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.DataModel.UserInformationDataModel;
import com.example.lnp.Interface.UserRecordsCallback;
import com.example.lnp.R;
import com.example.lnp.adapter.AllUserAdapter;
import com.example.lnp.adapter.FormAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    RecyclerView recyclerViewAllUser;
    AppCompatButton btnNext;
    AutoCompleteTextView spinnerPageNumber, spinnerSort;
    int pageCounter = 0;
    int totalPage = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //FindViewById
        recyclerViewAllUser = findViewById(R.id.recyclerAllUser);
        btnNext = findViewById(R.id.btnNext);
        spinnerPageNumber = findViewById(R.id.spinnerPageNumber);
        spinnerSort = findViewById(R.id.spinnerSort);

        String[] sorting = {
                "Ascending", "Descending"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sorting);
        spinnerSort.setAdapter(adapter);

        getUserRecords(new UserRecordsCallback() {
            @Override
            public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                AllUserAdapter userAdapter = new AllUserAdapter(Admin.this, userInformationDataModelArrayList);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewAllUser.setLayoutManager(manager);
                recyclerViewAllUser.setAdapter(userAdapter);
            }

            @Override
            public void pageCounter(int pageNumber) {
                totalPage=pageNumber;
                setUpPageNumberSpinner(pageNumber);
            }

            @Override
            public void onError(String error) {

            }
        },"0");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCounter++;
                Log.d("pageCounter","Curent Page :"+pageCounter+"\nTotal Page : "+totalPage);
                if(pageCounter<totalPage) {

                    getUserRecords(new UserRecordsCallback() {
                        @Override
                        public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                            AllUserAdapter userAdapter = new AllUserAdapter(Admin.this, userInformationDataModelArrayList);
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewAllUser.setLayoutManager(manager);
                            recyclerViewAllUser.setAdapter(userAdapter);
                        }

                        @Override
                        public void pageCounter(int pageNumber) {

                        }

                        @Override
                        public void onError(String error) {

                        }
                    }, String.valueOf(pageCounter));
                }else{
                    Toast.makeText(Admin.this, "There Is No More Page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinnerPageNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pageNumber= Integer.parseInt(spinnerPageNumber.getText().toString());
                pageNumber--;
                getUserRecords(new UserRecordsCallback() {
                    @Override
                    public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                        AllUserAdapter userAdapter = new AllUserAdapter(Admin.this, userInformationDataModelArrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewAllUser.setLayoutManager(manager);
                        recyclerViewAllUser.setAdapter(userAdapter);
                    }

                    @Override
                    public void pageCounter(int pageNumber) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                },String.valueOf(pageNumber));
            }
        });


    }


    public void getUserRecords(final UserRecordsCallback callback, String pageNumber) {

        ArrayList<UserInformationDataModel> userInformationDataModelArrayList = new ArrayList<>();

        // Initialize Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://192.168.1.3:8080/rest/user/users/" + pageNumber + "/10/id";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject record=jsonArray.getJSONObject(i);
                                UserInformationDataModel userInfo=new UserInformationDataModel();
                                userInfo.setEmail(record.getString("email"));
                                userInfo.setFirstName(record.getString("firstName"));
                                userInfo.setLastName(record.getString("lastName"));
                                userInfo.setGender(record.getString("gender"));
                                userInfo.setDateOfBirth(record.getString("dob"));
                                userInfo.setDesignation(record.getString("designation"));
                                String[] fullAddress=record.getString("address").split("#");
                                userInfo.setAddress1(fullAddress[0]);
                                userInfo.setState(fullAddress[1]);
                                userInfo.setDistrict(fullAddress[2]);
                                userInformationDataModelArrayList.add(userInfo);
                            }
                            callback.onSuccess(userInformationDataModelArrayList);
                            callback.pageCounter(response.getInt("totalPages"));
                        } catch (JSONException jsonException) {
                            callback.onError(jsonException.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(jsonObjectRequest);

    }

    private void setUpPageNumberSpinner(int totalPages) {
        ArrayList<String> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, pageNumbers);
        spinnerPageNumber.setAdapter(adapter);
    }
}