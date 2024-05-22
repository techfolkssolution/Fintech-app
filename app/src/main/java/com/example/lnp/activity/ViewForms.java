package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms);

        //FindViewByID
        recyclerViewForm=findViewById(R.id.recyclerViewForm);
        btnNext=findViewById(R.id.btnNext);

       getServiceRecords(new ServiceRecordsCallback() {
           @Override
           public void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
               FormAdapter formAdapter=new FormAdapter(ViewForms.this,utilityServiceModelArrayList);
               LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
               recyclerViewForm.setLayoutManager(manager);
               recyclerViewForm.setAdapter(formAdapter);
           }

           @Override
           public void onError(String error) {

           }
       },"0");

//       display();

       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                int pageCounter=0;
                pageCounter++;
                getServiceRecords(new ServiceRecordsCallback() {
                    @Override
                    public void onSuccess(ArrayList<UtilityServiceModel> utilityServiceModelArrayList) {
                        FormAdapter formAdapter=new FormAdapter(ViewForms.this,utilityServiceModelArrayList);
                        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerViewForm.setLayoutManager(manager);
                        recyclerViewForm.setAdapter(formAdapter);
                    }

                    @Override
                    public void onError(String error) {

                    }
                },String.valueOf(pageCounter));
           }
       });


    }



    public void getServiceRecords(final ServiceRecordsCallback callback,String pageNumber){
        ArrayList<UtilityServiceModel> utilityServiceModelArrayList=new ArrayList<>();
        // Initialize Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl="http://192.168.1.3:8080/rest/service/get/"+pageNumber+"/10/id";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("content");
                    Log.d("APIResponse","Array  : "+jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject record=jsonArray.getJSONObject(i);
                        UtilityServiceModel utilityServiceModel=new UtilityServiceModel();
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
                        utilityServiceModelArrayList.add(utilityServiceModel);
                    }
                    callback.onSuccess(utilityServiceModelArrayList);
                }catch (JSONException exception){
                    Log.d("APIResponse","Error : "+exception.getMessage());
                    callback.onError(exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APIResponse","Error : "+error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);

    }

}