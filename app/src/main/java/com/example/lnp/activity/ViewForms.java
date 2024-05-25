package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.UtilityServiceModel;
import com.example.lnp.Interface.ServiceRecordsCallback;
import com.example.lnp.R;
import com.example.lnp.adapter.FormAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewForms extends AppCompatActivity {
    RecyclerView recyclerViewForm;
    AppCompatButton btnNext;
    AutoCompleteTextView spinnerPageNumber,spinnerSort;
    int pageCounter = 0;
    int totalPage = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms);

        //FindViewByID
        recyclerViewForm = findViewById(R.id.recyclerViewForm);
        btnNext = findViewById(R.id.btnNext);
        spinnerPageNumber = findViewById(R.id.spinnerPageNumber);
        spinnerSort=findViewById(R.id.spinnerSort);

        String[] sorting={
               "Ascending","Descending"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sorting);
        spinnerSort.setAdapter(adapter);


        getServiceRecords(new ServiceRecordsCallback() {
            @Override
            public void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
                FormAdapter formAdapter = new FormAdapter(ViewForms.this, utilityServiceModelArrayList);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewForm.setLayoutManager(manager);
                recyclerViewForm.setAdapter(formAdapter);
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void pageCounter(int pageNumber) {
                totalPage=pageNumber;
                setUpPageNumberSpinner(pageNumber);
            }
        }, "0");


        spinnerPageNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pageNumber= Integer.parseInt(spinnerPageNumber.getText().toString());
                pageNumber--;
                getServiceRecords(new ServiceRecordsCallback() {
                    @Override
                    public void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
                        FormAdapter formAdapter = new FormAdapter(ViewForms.this, utilityServiceModelArrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewForm.setLayoutManager(manager);
                        recyclerViewForm.setAdapter(formAdapter);
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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pageCounter++;
                    getServiceRecords(new ServiceRecordsCallback() {
                        @Override
                        public void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
                            FormAdapter formAdapter = new FormAdapter(ViewForms.this, utilityServiceModelArrayList);
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewForm.setLayoutManager(manager);
                            recyclerViewForm.setAdapter(formAdapter);
                        }

                        @Override
                        public void onError(String error) {

                        }

                        @Override
                        public void pageCounter(int pageNumber) {

                        }
                    }, String.valueOf(pageCounter));
                }
        });


    }


    private void setUpPageNumberSpinner(int totalPages) {
        ArrayList<String> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, pageNumbers);
        spinnerPageNumber.setAdapter(adapter);
    }


    public void getServiceRecords(final ServiceRecordsCallback callback, String pageNumber) {
        ArrayList<UtilityServiceModel> utilityServiceModelArrayList = new ArrayList<>();
        // Initialize Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://192.168.1.3:8080/rest/service/get/" + pageNumber + "/10/id";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("content");
                    Log.d("APIResponse", "Number Of Page  : " + response.getInt("totalPages"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject record = jsonArray.getJSONObject(i);
                        UtilityServiceModel utilityServiceModel = new UtilityServiceModel();
                        utilityServiceModel.setId(record.getInt("id"));
                        utilityServiceModel.setName(record.getString("name"));
                        utilityServiceModel.setAddress(record.getString("address"));
                        utilityServiceModel.setMobileNumber(record.getLong("mobileNumber"));
                        utilityServiceModel.setLoanCategory(record.getString("loanCategory"));
                        utilityServiceModel.setLoanType(record.getString("loanType"));
                        utilityServiceModel.setLoanAmount(record.getLong("loanAmount"));
                        utilityServiceModel.setCAType(record.getString("caServiceType"));
                        utilityServiceModel.setEngineerBuildingType(record.getString("engineerBuildingType"));
                        utilityServiceModel.setEngineerServiceType(record.getString("engineerServiceType"));
                        utilityServiceModel.setTenure(record.getString("tenure"));
                        utilityServiceModel.setSavingAmount(record.getLong("savingAmount"));
                        utilityServiceModel.setComment(record.getString("comments"));
                        utilityServiceModel.setFormType(record.getString("formType"));
                        utilityServiceModelArrayList.add(utilityServiceModel);
                    }
                    callback.onSuccess(utilityServiceModelArrayList);
                    callback.pageCounter(response.getInt("totalPages"));
                } catch (JSONException exception) {
                    Log.d("APIResponse", "Error : " + exception.getMessage());
                    callback.onError(exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APIResponse", "Error : " + error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);

    }

}