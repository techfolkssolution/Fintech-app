package com.example.lnp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.AdminAccessInformation;
import com.example.lnp.Interface.OnItemClick;
import com.example.lnp.R;
import com.example.lnp.activity.Admin;
import com.example.lnp.activity.EditProfile;
import com.example.lnp.activity.RequestService;
import com.example.lnp.activity.RetailerDocumentVerification;
import com.example.lnp.activity.ViewForms;
import com.example.lnp.adapter.ServicesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends Fragment implements OnItemClick {
    private ImageSlider imageSlider;
    String phoneNumber = "";
    private String[] servicesName = {
            "Loans", "CA", "Engineer", "CIBIL", "Savings", "View Forms", "Admin"
    };
    private int[] servicesIcon = {
            R.drawable.bank, R.drawable.accountant, R.drawable.engineers, R.drawable.cibil, R.drawable.piggybank, R.drawable.form, R.drawable.admin
    };

    private String[] billsAndUtilityServiceName = {
            "Recharge", "PAN Card", "EMI", "Broadband", "Bill Payment", "Insurance", "Water", "DTH", "Landline", "Electricity", "Cable", "Fastag", "LPG"
    };

    private int[] billsAndUtilityServiceIcon = {
            R.drawable.recharge, R.drawable.pancard, R.drawable.emi,
            R.drawable.broadband, R.drawable.bill, R.drawable.insurance,
            R.drawable.water, R.drawable.dish, R.drawable.telephone,
            R.drawable.electricity, R.drawable.television, R.drawable.fastag, R.drawable.lpg
    };
    private RecyclerView recyclerViewQuery, recyclerViewBills;
    private ServicesAdapter queryAdapter, billAdapter;
    private TextView textViewRetailer;
    private ImageView imageViewGmail, imageViewPhone, imageViewWhatsapp;
    private SharedPreferences sharedPreferences;

    public Home() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.imageSlider);
        recyclerViewQuery = view.findViewById(R.id.recyclerViewServices);
        recyclerViewBills = view.findViewById(R.id.recyclerViewBills);
        textViewRetailer = view.findViewById(R.id.textViewRetailer);
        imageViewGmail = view.findViewById(R.id.imageViewGmail);
        imageViewPhone = view.findViewById(R.id.imageViewPhone);
        imageViewWhatsapp = view.findViewById(R.id.imageViewWhatsapp);


        //Display Images in slider.
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        sharedPreferences= requireContext().getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        int userRole=sharedPreferences.getInt("userRole",100);
        if(userRole==0){
            textViewRetailer.setVisibility(View.VISIBLE);
        }else{
            textViewRetailer.setVisibility(View.GONE);
        }

        displayAssistanceQueryItem();
        displayBillsAndUtilityItem();
        textViewRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RetailerDocumentVerification.class);
                startActivity(i);

            }
        });

        imageViewGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getCustomerServiceData("gmail", new CallBack() {
                   @Override
                   public void onSuccess(String data) {
                       Intent intent = new Intent(Intent.ACTION_SENDTO);
                       intent.setData(Uri.parse("mailto:"+data)); // only email apps should handle this
                       startActivity(intent);
                   }
               });
            }
        });
        imageViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCustomerServiceData("phone", new CallBack() {
                    @Override
                    public void onSuccess(String data) {
                     phoneNumber="tel:+91"+data;
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(phoneNumber));
                        startActivity(intent);
                    }
                });


            }
        });

        imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCustomerServiceData("whatsapp", new CallBack() {
                    @Override
                    public void onSuccess(String data) {
                        String url="https://wa.me/+91"+data+"?text=Hi";
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }
        });


        return view;
    }

    public void getCustomerServiceData(String customerServiceType,CallBack callBack) {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, API.API_CONTACT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject data=response.getJSONObject(i);
                        String value=data.getString("adminAccessInformationValue");
                        String key=data.getString("adminAccessInformationKey");
                        if(key.equals(customerServiceType)){
                            callBack.onSuccess(value);
                            break;
                        }

                    }catch (JSONException jsonException){

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }
    public interface CallBack{
        public void onSuccess(String data);
    }

    public void displayAssistanceQueryItem() {
        queryAdapter = new ServicesAdapter(getActivity(), servicesName, servicesIcon, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewQuery.setLayoutManager(layoutManager);
        recyclerViewQuery.setAdapter(queryAdapter);
    }

    public void displayBillsAndUtilityItem() {
        billAdapter = new ServicesAdapter(getActivity(), billsAndUtilityServiceName, billsAndUtilityServiceIcon, this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerViewBills.setLayoutManager(layoutManager);
        recyclerViewBills.setAdapter(billAdapter);
    }

    @Override
    public void onItemClickListener(String serviceName) {
        boolean isRetailer = false;
        if (serviceName.equals("View Forms")) {
            Intent intent = new Intent(getActivity(), ViewForms.class);
            intent.putExtra("serviceName", serviceName);
            startActivity(intent);
        } else if (serviceName.equals("Admin")) {
            Intent intent = new Intent(getActivity(), Admin.class);
            intent.putExtra("serviceName", serviceName);
            startActivity(intent);
        } else {
            if (isRetailer || isServiceAccessibleByAll(serviceName)) {
                Intent i = new Intent(getActivity(), RequestService.class);
                i.putExtra("serviceName", serviceName);
                startActivity(i);
            } else {
                Toast.makeText(getActivity(), "This service is only available to retailers.", Toast.LENGTH_SHORT).show();
            }
        }


//        Toast.makeText(getActivity(), "Service Name : "+serviceName, Toast.LENGTH_SHORT).show();
    }


    private boolean isServiceAccessibleByAll(String serviceName) {
        // Define which services are accessible by all users
        return serviceName.equals("Loans") || serviceName.equals("CA") || serviceName.equals("Engineer")
                || serviceName.equals("CIBIL") || serviceName.equals("Savings");
    }
}