package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class AllUserInformation extends AppCompatActivity {
    RecyclerView recyclerViewAllUser;
    AppCompatButton btnNext;
    AutoCompleteTextView spinnerPageNumber, spinnerSort;
    int pageCounter = 0;
    int totalPage = 0;
    EditText editTextSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_information);

        //FindViewById
        recyclerViewAllUser = findViewById(R.id.recyclerAllUser);
        btnNext = findViewById(R.id.btnNext);
        spinnerPageNumber = findViewById(R.id.spinnerPageNumber);
        spinnerSort = findViewById(R.id.spinnerSort);
        editTextSearch = findViewById(R.id.editTextSearch);

        String[] sorting = {
                "Ascending", "Descending"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sorting);
        spinnerSort.setAdapter(adapter);


        getUserRecords(new UserRecordsCallback() {
            @Override
            public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewAllUser.setLayoutManager(manager);
                recyclerViewAllUser.setAdapter(userAdapter);
            }

            @Override
            public void pageCounter(int pageNumber) {
                totalPage = pageNumber;
                setUpPageNumberSpinner(pageNumber);
            }

            @Override
            public void onError(String error) {

            }
        }, "0","Ascending");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sortBy = spinnerSort.getText().toString();
                pageCounter++;
                Log.d("pageCounter", "Curent Page :" + pageCounter + "\nTotal Page : " + totalPage);
                if (pageCounter < totalPage) {

                    getUserRecords(new UserRecordsCallback() {
                        @Override
                        public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                            AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
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
                    }, String.valueOf(pageCounter),sortBy);
                } else {
                    Toast.makeText(AllUserInformation.this, "There Is No More Page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinnerPageNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sortBy = spinnerSort.getText().toString();
                int pageNumber = Integer.parseInt(spinnerPageNumber.getText().toString());
                pageNumber--;
                getUserRecords(new UserRecordsCallback() {
                    @Override
                    public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                        AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
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
                }, String.valueOf(pageNumber),sortBy);
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                String sortBy = spinnerSort.getText().toString();
                if (query.length() == 0) {
                    getUserRecords(new UserRecordsCallback() {
                        @Override
                        public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                            AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewAllUser.setLayoutManager(manager);
                            recyclerViewAllUser.setAdapter(userAdapter);
                        }

                        @Override
                        public void pageCounter(int pageNumber) {
                            totalPage = pageNumber;
                            setUpPageNumberSpinner(pageNumber);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    }, "0",sortBy);
                } else {
                    searchUser(new UserRecordsCallback() {
                        @Override
                        public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                            AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
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
                    }, query);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinnerSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sortBy = sorting[i];

                getUserRecords(new UserRecordsCallback() {
                    @Override
                    public void onSuccess(ArrayList<UserInformationDataModel> userInformationDataModelArrayList) {
                        AllUserAdapter userAdapter = new AllUserAdapter(AllUserInformation.this, userInformationDataModelArrayList);
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
                }, "0",sortBy);
            }
        });

    }

    public void getUserRecords(final UserRecordsCallback callback, String pageNumber,String sortBy) {
        String apiUrl="";


        ArrayList<UserInformationDataModel> userInformationDataModelArrayList = new ArrayList<>();

        // Initialize Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        if(sortBy.equals("Ascending")){
            apiUrl = "http://" + API.IP_ADDRESS + ":8080/rest/user/users/" + pageNumber + "/10/id";
        } else if (sortBy.equals("Descending")) {
            apiUrl = "http://" + API.IP_ADDRESS + ":8080/rest/user/users/desc/" + pageNumber + "/10/id";
        }else{
            apiUrl = "http://" + API.IP_ADDRESS + ":8080/rest/user/users/" + pageNumber + "/10/id";
        }




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject record = jsonArray.getJSONObject(i);
                                UserInformationDataModel userInfo = new UserInformationDataModel();
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


    public void searchUser(final UserRecordsCallback callback, String query) {
        ArrayList<UserInformationDataModel> userInformationDataModelArrayList = new ArrayList<>();

        // Initialize Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        //http://localhost:8080/rest/user/getAllUsersByName/K

        String apiUrl = "http://" + API.IP_ADDRESS + ":8080/rest/user/getAllUsersByName/" + query;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject record = response.getJSONObject(i);
                        UserInformationDataModel userInfo = new UserInformationDataModel();
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
                    }

                    callback.onSuccess(userInformationDataModelArrayList);

                } catch (JSONException e) {
                    callback.onError(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);


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